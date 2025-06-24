import { Fragment, useState } from "react";
import "./ListGroup.css";

const DEFAULT_PLACEHOLDER =
  "https://via.placeholder.com/240x320?text=No+Image"; // 3:4 placeholder

function ListGroup({ items }) {
    const [selectedIndex, setSelectedIndex] = useState(null);

    if (!items || items.length === 0) {
        return <p>No items found</p>;
    }

    return (
        <Fragment>
            <ul className="list-group">
                {items.map((item, index) => (
                    <li
                        className={
                            "list-group-item" +
                            (index === selectedIndex ? " active" : "")
                        }
                        key={item.title}
                        onClick={() => setSelectedIndex(index)}
                        style={{ cursor: "pointer" }}
                    >
                        <div className="list-group-item-content">
                            <div className="list-group-item-image">
                                <img
                                    src={item.image ? item.image : DEFAULT_PLACEHOLDER}
                                    alt={item.title}
                                />
                            </div>
                            <span className="list-group-item-title">{item.title}</span>
                        </div>
                    </li>
                ))}
            </ul>
        </Fragment>
    );
}

export default ListGroup;
