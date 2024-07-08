import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Electronic from "../components/Electronic";
import { setMessages } from "../reducers/message-reducer";
import Collapsible from "../components/Collapsible";
import moment from "moment/moment";

function ElectronicList() {

  const dispatch = useDispatch()

  const { electronics } = useSelector(state => state.electronics);

  useEffect(() => {

    const eventSource = new EventSource('http://localhost:8080/open-sse');

    eventSource.onmessage = (event) => {

      const newMessage = {
        id: event.lastEventId,
        data: JSON.parse(event.data)
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

  const formatDate = (date) => {
    console.log(moment(date).format('YYYY-MM-DDTHH:mm:ss'))
    return moment(date).format('YYYY-MM-DDTHH:mm:ss')
  };

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
              <p>{formatDate(consumption.initialTime)} - {formatDate(consumption.endTime)}</p>
            ))}
          </Collapsible>
        </div>
      ))}

    </div>
  );
}

export default ElectronicList;