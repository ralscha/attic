/*
 * An example provided by tutorial reader Olivier Berlanger.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GenealogyExample extends JFrame {
    GenealogyTree tree;

    public GenealogyExample() {
        super("Genealogy tree demo");
        // add window listener
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        // construct the panel with the toggle buttons
        JRadioButton showDescendant = 
                new JRadioButton("Show descendants", true);
        final JRadioButton showAncestor = 
                new JRadioButton("Show ancestors");
        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(showDescendant);
        bGroup.add(showAncestor);
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == showAncestor) {
                    tree.showAncestor(true);
                }
                else {
                    tree.showAncestor(false);
                }
            }
        };
        showDescendant.addActionListener(al);
        showAncestor.addActionListener(al);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showDescendant);
        buttonPanel.add(showAncestor);

        // construct the tree
        tree = new GenealogyTree(getGenealogyGraph());
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        // construct the content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    /**
     *  Constructs the genealogy graph used by the model.
     */
    public Person getGenealogyGraph() {
        //the greatgrandparent generation
        Person a1 = new Person("Jack (great-granddaddy)");
        Person a2 = new Person("Jean (great-granny)");
        Person a3 = new Person("Albert (great-granddaddy)");
        Person a4 = new Person("Rae (great-granny)");
        Person a5 = new Person("Paul (great-granddaddy)");
        Person a6 = new Person("Josie (great-granny)");

        //the grandparent generation
        Person b1 = new Person("Peter (grandpa)");
        Person b2 = new Person("Zoe (grandma)");
        Person b3 = new Person("Simon (grandpa)");
        Person b4 = new Person("James (grandpa)");
        Person b5 = new Person("Bertha (grandma)");
        Person b6 = new Person("Veronica (grandma)");
        Person b7 = new Person("Anne (grandma)");
        Person b8 = new Person("Renee (grandma)");
        Person b9 = new Person("Joseph (grandpa)");

        //the parent generation
        Person c1 = new Person("Isabelle (mom)");
        Person c2 = new Person("Frank (dad)");
        Person c3 = new Person("Louis (dad)");
        Person c4 = new Person("Laurence (dad)");
        Person c5 = new Person("Valerie (mom)");
        Person c6 = new Person("Marie (mom)");
        Person c7 = new Person("Helen (mom)");
        Person c8 = new Person("Mark (dad)");
        Person c9 = new Person("Oliver (dad)");

        //the youngest generation
        Person d1 = new Person("Clement (boy)");
        Person d2 = new Person("Colin (boy)");

        Person.linkFamily(a1,a2,new Person[] {b1,b2,b3,b4});
        Person.linkFamily(a3,a4,new Person[] {b5,b6,b7});
        Person.linkFamily(a5,a6,new Person[] {b8,b9});
        Person.linkFamily(b3,b6,new Person[] {c1,c2,c3});
        Person.linkFamily(b4,b5,new Person[] {c4,c5,c6});
        Person.linkFamily(b8,b7,new Person[] {c7,c8,c9});
        Person.linkFamily(c4,c7,new Person[] {d1,d2});

        return a1;
    }

    public static void main(String[] args) {
        // create a frame
        JFrame mainFrame = new GenealogyExample();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
