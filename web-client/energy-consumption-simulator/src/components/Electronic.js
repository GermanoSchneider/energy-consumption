import { useDispatch, useSelector } from "react-redux";
import { findAllElectronics, powerOff, powerOn } from "../api";
import { setElectronics } from "../reducers/electronics-reducer";
import { convertSeconds } from "../utils";

function Electronic({electronicId, name, power, status}) {

    const style = {
        backgroundColor: status === 'ON' ? '#0EBB35' : '#E31111'
    }

    const { messages } = useSelector(state => state.messages);

    const dispatch = useDispatch();

    const powerElectronic = async () => {

        if (status === 'ON') {
            await powerOff(electronicId).then(async () => await updateElectronics());
        } else {
            await powerOn(electronicId).then(async () => await updateElectronics());
        }
    }

    const updateElectronics = async () => await findAllElectronics().then(response => dispatch(setElectronics(response.data))) 
    
    const buildElectronicData = (messages) => {
        let filteredMessages = messages.find((message) => message.id == electronicId);
        if (filteredMessages) {
            return filteredMessages.data + "kW - " + convertSeconds(filteredMessages.seconds);
        }
    }

    return (
        <div className="electronic-box">
            <div className="electronic-item">
                <div style={style} className="electronic-item-action">
                    <p onClick={powerElectronic}>{status}</p>
                </div>
                <div className="electronic-item-info">
                    <p>{name}</p>
                    <p>{power} kW</p>
                </div>
            </div>
            <div className="consumption">
                <p>{buildElectronicData(messages)}</p>
            </div>
        </div>
    )
}


export default Electronic;