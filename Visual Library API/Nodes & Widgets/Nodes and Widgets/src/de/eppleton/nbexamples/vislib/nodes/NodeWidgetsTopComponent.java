/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.nbexamples.vislib.nodes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.util.Collections;
import java.util.Set;
import javax.swing.JLabel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneEventType;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//de.eppleton.nbexamples.vislib.nodes//NodeWidgets//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "NodeWidgetsTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "de.eppleton.nbexamples.vislib.nodes.NodeWidgetsTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_NodeWidgetsAction",
preferredID = "NodeWidgetsTopComponent")
@Messages({
    "CTL_NodeWidgetsAction=NodeWidgets",
    "CTL_NodeWidgetsTopComponent=NodeWidgets Window",
    "HINT_NodeWidgetsTopComponent=This is a NodeWidgets window"
})
public final class NodeWidgetsTopComponent extends TopComponent {

    ExplorerManager em;
    private final Node root;
    private final LayerWidget widgetLayer;

    public NodeWidgetsTopComponent() {
        initComponents();
        setName(Bundle.CTL_NodeWidgetsTopComponent());
        setToolTipText(Bundle.HINT_NodeWidgetsTopComponent());
        setLayout(new BorderLayout());
        
        // the Childfactory creates Nodes for selected Objects 
        final FakeChildFactory childFactory = new FakeChildFactory();
        // A Root Context Node for the ExplorerManager
        root = new AbstractNode(Children.create(childFactory, false));
        // ExplorerManager will create the Proxy Lookups for multiple selected Nodes
        em = new ExplorerManager();
        em.setRootContext(root);
        // connect to global selection management
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        // the scene
        ObjectScene scene = new ObjectScene();
        // Interaction Layer for selection
        LayerWidget backgroundLayer = new LayerWidget(scene);
        scene.addChild(backgroundLayer);
        // A layer to hold the Widgets
        widgetLayer = new LayerWidget(scene);
        scene.addChild(widgetLayer);
        // Make possible to select Widgets / Objects
        scene.getActions().addAction(ActionFactory.createRectangularSelectAction(scene, backgroundLayer));

        scene.addObjectSceneListener(new ObjectSceneListener() {
            @Override
            public void objectAdded(ObjectSceneEvent event, Object addedObject) {
            }

            @Override
            public void objectRemoved(ObjectSceneEvent event, Object removedObject) {
            }

            @Override
            public void objectStateChanged(ObjectSceneEvent event, Object changedObject, ObjectState previousState, ObjectState newState) {
            }

            @Override
            public void selectionChanged(ObjectSceneEvent event, Set<Object> previousSelection, Set<Object> newSelection) {
                childFactory.setKeys(newSelection);
                try {
                    em.setSelectedNodes(root.getChildren().getNodes());
                } catch (PropertyVetoException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

            @Override
            public void highlightingChanged(ObjectSceneEvent event, Set<Object> previousHighlighting, Set<Object> newHighlighting) {
            }

            @Override
            public void hoverChanged(ObjectSceneEvent event, Object previousHoveredObject, Object newHoveredObject) {
            }

            @Override
            public void focusChanged(ObjectSceneEvent event, Object previousFocusedObject, Object newFocusedObject) {
            }
        }, ObjectSceneEventType.OBJECT_SELECTION_CHANGED);

        // add some Nodes to the scene
        initScene(scene);
        add(scene.createView(), BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    private void initScene(ObjectScene scene) {
        ObjectSelectProvider selectProvider = new ObjectSelectProvider(scene);
        WidgetAction selectAction = ActionFactory.createSelectAction(selectProvider);
        WidgetAction moveAction = ActionFactory.createMoveAction();
        LabelWidget one = new LabelWidget(scene, "I'm a Widget") {
            @Override
            protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
                super.notifyStateChanged(previousState, state);
                setBorder(state.isSelected() ? BorderFactory.createResizeBorder(6) : BorderFactory.createEmptyBorder(4));
            }
        };
        one.getActions().addAction(moveAction);
        one.getActions().addAction(selectAction);
        scene.addObject(new JLabel("I'm a Node"), one);
        one.setPreferredLocation(new Point(10, 10));
        widgetLayer.addChild(one);

        LabelWidget two = new LabelWidget(scene, "I'm also a Widget") {
            @Override
            protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
                super.notifyStateChanged(previousState, state);
                setBorder(state.isSelected() ? BorderFactory.createResizeBorder(6) : BorderFactory.createEmptyBorder(4));
            }
        };
        two.getActions().addAction(moveAction);
        two.getActions().addAction(selectAction);
        scene.addObject(
                new JLabel("I'm also a Node"), two);
        two.setPreferredLocation(
                new Point(100, 10));
        widgetLayer.addChild(two);
    }
    private class ObjectSelectProvider implements SelectProvider {
        
        ObjectScene scene;

        public ObjectSelectProvider(ObjectScene scene) {
            this.scene = scene;
        }
        
        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }
        
        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }
        
        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            Object object = scene.findObject(widget);
            
            if (object != null) {
                if (scene.getSelectedObjects().contains(object))return;
                scene.userSelectionSuggested(Collections.singleton(object), invertSelection);
            } else
                scene.userSelectionSuggested(Collections.emptySet(), invertSelection);
        }
    }
}
