package com.wontlost.dicebear;

import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wontlost.dicebear.Constants.Style;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static com.wontlost.ckeditor.utils.Constant.PAGE_DEMO_DICEBEAR;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@Route(value = PAGE_DEMO_DICEBEAR)
@PageTitle("Avatar")
public class DicebearView extends VerticalLayout {

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    //PaperSlider radius = new PaperSlider(0, 50, 0);

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private void changeColor(ValueChangeEvent event, Options options, DicebearVaadin dicebearVaadin) {
        if(event.getValue().equals(Style.initials) /*|| radius.getValue().compareTo(0)>0*/) {
            options.setBackground(nextColor());
        }else{
            options.setBackground("transparent");
        }
        dicebearVaadin.setOptions(options);
    }

    private String nextColor() {
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        return String.format("#%06x", nextInt);
    }

    public DicebearView() {
        super();
        Options options = new Options();
        TextField value = new TextField("Value");
        value.setPlaceholder("wontlost");
        TextField size = new TextField("Size");
        size.setPlaceholder("100");
        Select<Style> select = new Select<>();
        select.setLabel("Style");
        List<Style> styleList = Arrays.asList(Style.values());
        select.setItemLabelGenerator(Style::name);
        select.setItems(styleList);
        select.setValue(Style.avataaars);

        add(new H3("Try it yourself"));
//        DicebearVaadin dicebearVaadin/* = new DicebearVaadin()*/;
//        Image image = new Image("icons/icon.png", "image");
//		image.setWidth("30");
//		image.setHeight("30");
//        dicebearVaadin = new DicebearVaadin(image);
        DicebearVaadin dicebearVaadin = new DicebearVaadin();
        dicebearVaadin.setWidth("100px");
        dicebearVaadin.setHeight("100px");
        dicebearVaadin.setStyle(Style.avataaars);
//        options.setBackground("white").setDataUri(false)
//                .setWidth(100).setHeight(100).setMargin(0).setRadius(50);

        add(select, value, size, /*radius,*/ dicebearVaadin);
        value.setValueChangeMode(ValueChangeMode.EAGER);
        value.addValueChangeListener(e-> {
            options.setValue(e.getValue());
            changeColor(e, options, dicebearVaadin);
        });
        /*radius.addValueChangeListener(e->{
            options.setRadius(radius.getValue());
            options.setMargin(radius.getValue()/5);
            changeColor(e, options, dicebearVaadin);
            dicebearVaadin.setOptions(options);
        });*/
        size.setValueChangeMode(ValueChangeMode.EAGER);
        size.addValueChangeListener(e-> {
                    if(isNumeric(e.getValue())) {
                        options.setWidth(Integer.parseInt(e.getValue()));
                        options.setHeight(Integer.parseInt(e.getValue()));
                        changeColor(e, options, dicebearVaadin);
                    } else {
                        Div content = new Div();
                        content.addClassName("size-style");
                        content.setText("Input size is not a number!");

                        Notification notification = new Notification(content);
                        notification.setDuration(3000);

// @formatter:off
                        String styles = ".size-style { "
                                + "  color: red;"
                                + " }";
// @formatter:on

                        /*
                         * The code below register the style file dynamically. Normally you
                         * use @StyleSheet annotation for the component class. This way is
                         * chosen just to show the style file source code.
                         */
                        //StreamRegistration resource = UI.getCurrent().getSession()
                        //        .getResourceRegistry()
                        //        .registerResource(new StreamResource("styles.css", () -> {
                        //            byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
                        //            return new ByteArrayInputStream(bytes);
                        //        }));
                        //UI.getCurrent().getPage().addStyleSheet(
                        //        "base://" + resource.getResourceUri().toString());
                        //notification.open();
                    }
                });

        select.addValueChangeListener(event -> {
            dicebearVaadin.setStyle(event.getValue());
            changeColor(event, options, dicebearVaadin);
        });
        setAlignItems(Alignment.CENTER);
    }

}
