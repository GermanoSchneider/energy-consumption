import { useState } from "react";

function Collapsible({title, children}) {

    const [isOpen, setIsOpen] = useState(false);

    const toggleCollapse = () => {
        setIsOpen(!isOpen)
    }

    return (
        <div className="collapsible">
            <button onClick={toggleCollapse} className="collapsible-header">
                {title}
            </button>
            <div className={`collapsible-content ${isOpen ? 'open' : 'closed'}`}>
                {children}
            </div>
        </div>
    )
}

export default Collapsible;