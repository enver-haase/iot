package org.artur.iot;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;

import org.artur.iot.data.Room;

@Tag("room-info")
public class RemoteRoomInfo extends Component {

    public RemoteRoomInfo() {
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        Workaround.addJSModule(attachEvent.getUI(),
                "/iot-roominfo/web-component/room-info.js");
    }

    public RemoteRoomInfo(Room room) {
        setRoom(room);
    }

    public void setRoom(Room room) {
        getElement().setProperty("room", room.getRoom());
    }
}
