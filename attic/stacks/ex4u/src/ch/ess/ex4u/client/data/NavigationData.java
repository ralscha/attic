package ch.ess.ex4u.client.data;

import ch.ess.ex4u.client.gui.RoleEditPanel;
import ch.ess.ex4u.client.gui.UserEditPanel;
import ch.ess.ex4u.client.gui.VertragEditPanel;
import ch.ess.ex4u.client.gui.ZeitEditPanel;
import ch.ess.ex4u.client.gui.samples.DialogsSample;
import ch.ess.ex4u.client.gui.samples.ProgressBarSample;
import ch.ess.ex4u.client.gui.samples.SliderSample;
import ch.ess.ex4u.client.gui.samples.TypeReuseSample;

public class NavigationData {

  private String idSuffix;

  public NavigationData(String idSuffix) {
    this.idSuffix = idSuffix;
  }

  private NavigationTreeNode[] data;

  private NavigationTreeNode[] getData() {
    if (data == null) {
      data = new NavigationTreeNode[]{
          new NavigationTreeNode("Zeiterfassung", "zeiterfassung-category", "root", "ess/16/clock.png", null, true, idSuffix),
          new NavigationTreeNode("Verträge", "zeiterfassung-vertraege", "zeiterfassung-category", "silk/database_table.png", new VertragEditPanel.Factory(), true, idSuffix),
          new NavigationTreeNode("Zeitgefässe", "zeiterfassung-zeitgefaesse", "zeiterfassung-category", "silk/database_gear.png", new VertragEditPanel.Factory(), true, idSuffix),
          new NavigationTreeNode("Zeit", "zeiterfassung-zeit", "zeiterfassung-category", "silk/calendar_view_day.png", new ZeitEditPanel.Factory(), true, idSuffix),
          
          new NavigationTreeNode("Customizzing", "customizzing-category", "root", "pieces/16/cube_frame.png", null, true, idSuffix),
          new NavigationTreeNode("Benutzer", "customizzing-benutzer", "customizzing-category", "icons/16/person.png", new UserEditPanel.Factory(), true, idSuffix),
          new NavigationTreeNode("Rollen", "customizzing-rollen", "customizzing-category", "icons/16/person_into.png", new RoleEditPanel.Factory(), true, idSuffix),

          new NavigationTreeNode("Other Controls", "controls-category", "root", "silk/timeline_marker.png", null, true, idSuffix),
          new NavigationTreeNode("Dialogs", "controls-category-dialogs", "controls-category", null, new DialogsSample.Factory(), true, idSuffix),
          new NavigationTreeNode("Slider", "controls-category-slider", "controls-category", null, new SliderSample.Factory(), true, idSuffix),
          new NavigationTreeNode("Progressbar", "controls-category-progressbar", "controls-category", null, new ProgressBarSample.Factory(), true, idSuffix),
          new NavigationTreeNode("DataType Reuse", "form-type-reuse", "controls-category", "silk/database_table.png", new TypeReuseSample.Factory(), true, idSuffix),

          new CommandTreeNode("Developer Console", "debug-category", "root", "silk/bug.png", new DebugConsoleCommand(), true, idSuffix)};
    }
    return data;
  }

  public static NavigationTreeNode[] getData(String idSuffix) {
    return new NavigationData(idSuffix).getData();
  }
}
