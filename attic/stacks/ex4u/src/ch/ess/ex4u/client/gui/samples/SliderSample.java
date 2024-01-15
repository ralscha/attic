package ch.ess.ex4u.client.gui.samples;

import ch.ess.ex4u.client.PanelFactory;
import ch.ess.ex4u.client.gui.BasePanel;
import ch.ess.ex4u.client.gui.MainLayout;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Slider;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.ValueChangedEvent;
import com.smartgwt.client.widgets.events.ValueChangedHandler;

public class SliderSample extends BasePanel {

  public SliderSample(MainLayout parent) {
    super(parent);
  }

  private static final String DESCRIPTION = "Move either Slider to update the other. You can change the value by clicking and dragging the thumb, "
      + "clicking on the track, or using the keyboard (once you've focused on one of the sliders).";

  public static class Factory implements PanelFactory {

    private String id;

    public Canvas create(MainLayout main) {
      SliderSample panel = new SliderSample(main);
      id = panel.getID();
      return panel;
    }

    public String getID() {
      return id;
    }

    public String getDescription() {
      return DESCRIPTION;
    }
  }

  @Override
  public Canvas getViewPanel() {

    Canvas canvas = new Canvas();

    final Slider vSlider = new Slider("Rating");
    vSlider.setMinValue(1);
    vSlider.setMaxValue(5);
    vSlider.setNumValues(5);
    vSlider.setHeight(300);
    canvas.addChild(vSlider);

    final Slider hSlider = new Slider("Rating");
    hSlider.setVertical(false);
    hSlider.setMinValue(1);
    hSlider.setMaxValue(5);
    hSlider.setNumValues(5);
    hSlider.setTop(200);
    hSlider.setLeft(100);
    canvas.addChild(hSlider);

    hSlider.addDrawHandler(new DrawHandler() {

      public void onDraw(@SuppressWarnings("unused") DrawEvent event) {
        vSlider.addValueChangedHandler(new ValueChangedHandler() {

          public void onValueChanged(@SuppressWarnings("hiding") ValueChangedEvent event) {
            int value = event.getValue();
            if (hSlider.getValue() != value) {
              hSlider.setValue(value);
            }
          }
        });
      }
    });

    hSlider.addDrawHandler(new DrawHandler() {

      public void onDraw(@SuppressWarnings("unused") DrawEvent event) {
        hSlider.addValueChangedHandler(new ValueChangedHandler() {

          public void onValueChanged(@SuppressWarnings("hiding") ValueChangedEvent event) {
            int value = event.getValue();
            if (vSlider.getValue() != value) {
              vSlider.setValue(value);
            }
          }
        });
      }
    });

    return canvas;
  }

  @Override
  public String getIntro() {
    return DESCRIPTION;
  }

}