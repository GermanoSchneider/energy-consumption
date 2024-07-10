import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Collapsible from "../components/Collapsible";
import Electronic from "../components/Electronic";
import { setMessages } from "../reducers/message-reducer";
import { convertSeconds } from "../utils";

function ElectronicList() {

  const dispatch = useDispatch()

  const { electronics } = useSelector(state => state.electronics);

  useEffect(() => {

    const eventSource = new EventSource('http://localhost:8080/open-sse');

    eventSource.onmessage = (event) => {

      const newMessage = {
        id: event.lastEventId,
        data: JSON.parse(JSON.parse(event.data).kilowatts),
        seconds: JSON.parse(JSON.parse(event.data).time)
      }

      dispatch(setMessages(newMessage))
    };

    eventSource.onerror = (event) => {
      console.error('SSE error:', event);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, [dispatch]);

  return (
    <div>
      {electronics.map((electronic, index) => (
        <div>
          <Electronic
            key={index}
            electronicId={electronic.id}
            name={electronic.name}
            power={electronic.power}
            status={electronic.status}
          />
          <Collapsible title={electronic.consumptions.length + ' consumptions'}>
            {electronic.consumptions.map((consumption, index) => (
              <p key={index}>{index} - <b>{consumption.kilowatts} kW</b> in {convertSeconds(consumption.seconds)}</p>
            ))}
          </Collapsible>
        </div>
      ))}

    </div>
  );
}

export default ElectronicList;