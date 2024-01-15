import java.awt.*;
import java.io.*;
class Main extends Frame implements FilenameFilter {
FileDialog fd;
TextField tfDirectory = new TextField();
TextField tfFile = new TextField();
TextField tfFilter = new TextField();

Main() {
super("FileDialog Example");
add("West", new Button("Load"));
add("East", new Button("Save"));

Panel p = new Panel();
p.setLayout(new GridBagLayout());
addRow(p, new Label("directory:", Label.RIGHT), tfDirectory);
addRow(p, new Label("file:", Label.RIGHT), tfFile);
addRow(p, new Label("filter:", Label.RIGHT), tfFilter);

add("South", p);
pack();
show();
}

// Adds a row in a gridbag layout where the c2 is stretchy
// and c1 is not.
void addRow(Container cont, Component c1, Component c2) {
GridBagLayout gbl = (GridBagLayout)cont.getLayout();
GridBagConstraints c = new GridBagConstraints();
Component comp;

c.fill = GridBagConstraints.BOTH;
cont.add(c1);
gbl.setConstraints(c1, c);

c.gridwidth = GridBagConstraints.REMAINDER;
c.weightx = 1.0;
cont.add(c2);
gbl.setConstraints(c2, c);
}

public boolean accept(File dir, String name) {
System.out.println("File "+dir+" String "+name);
if (fd.getMode() == FileDialog.LOAD) {
return name.lastIndexOf(tfFilter.getText()) > 0;
}
return true;
}

public boolean action(Event evt, Object what) {
boolean load = "Load".equals(what);

if (load || "Save".equals(what)) {
FileDialog fd = new FileDialog(this, null,
load ? FileDialog.LOAD : FileDialog.SAVE);
fd.setDirectory(tfDirectory.getText());
fd.setFile(tfFile.getText());
fd.setFilenameFilter(this);
fd.show();
tfDirectory.setText(fd.getDirectory());
tfFile.setText(fd.getFile());

// Filter must be the same
if (fd.getFilenameFilter() != this) {
throw new RuntimeException("Internal error");
}
return true;
}
return false;
}

static public void main(String[] args) {
new Main();
}

}