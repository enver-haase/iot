package org.artur.iot;

import org.artur.iot.view.allrooms.AllRooms;
import org.artur.iot.view.dashboard.Dashboard;
import org.artur.iot.view.floorplan.RemoteFloorplan;
import org.artur.iot.view.roomsetup.RoomSetup;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.InitialPageSettings.WrapMode;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinService;

@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base", enableInstallPrompt = false)
@CssImport("./mainlayout.css")
public class MainLayout extends AppLayout implements PageConfigurator {

    private Tabs tabs;

    public MainLayout() {
        H1 header = new H1("In Da House");
        header.getStyle().set("margin-left", "0.5em");
        addToNavbar(true, new DrawerToggle());
        addToDrawer(header);

        tabs = new Tabs();
        tabs.add(createNavigationTab(Dashboard.class, VaadinIcon.DASHBOARD,
                "Dashboard"));
        tabs.add(createNavigationTab(RemoteFloorplan.class,
                VaadinIcon.THIN_SQUARE, "Floorplan"));
        tabs.add(createNavigationTab(AllRooms.class, VaadinIcon.STOCK,
                "All rooms"));
        tabs.add(createNavigationTab(RoomSetup.class, VaadinIcon.COGS,
                "Sensor and room setup"));
        tabs.setOrientation(Orientation.VERTICAL);
        tabs.setThemeName("minimal");
        tabs.getStyle().set("margin", "0 auto");
        tabs.getStyle().set("flex", "1");

        addToDrawer(tabs);
    }

    private void highlight(RouterLink link, boolean active) {
        if (active) {
            tabs.setSelectedTab((Tab) link.getParent().get());
        }
    }

    private Tab createNavigationTab(Class<? extends Component> targetClass,
            VaadinIcon icon, String text) {
        Tab tab = new Tab();
        RouterLink link = new RouterLink();
        link.setHighlightCondition((l, event) -> {
            return targetClass
                    .isAssignableFrom(event.getActiveChain().get(0).getClass());
        });
        link.setHighlightAction(this::highlight);
        link.setRoute(VaadinService.getCurrent().getRouter(), targetClass);
        link.add(icon.create(), new Text(text));
        tab.add(link);

        return new Tab(link);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addInlineWithContents(
                "<script type='module' src='/iot-bundle/VAADIN/build/index.nocache.js'></script>", WrapMode.NONE);
    }
}
