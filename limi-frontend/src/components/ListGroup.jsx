import { Fragment } from "react";

function ListGroup() {
    const items = ["An item", "A second item", "A third item", "A fourth item", "And a fifth one"];

    if (items.length === 0) {
        return <p>No items found</p>;
    }

    return (
        <Fragment>
            <h1>List</h1>
            <ul className="list-group">
                {items.map((item, index) => (
                    <li
                        className="list-group-item"
                        key={item}
                        onClick={() => console.log(item, index)}
                    >
                        {item}
                    </li>
                ))}
            </ul>
        </Fragment>
    );
}

export default ListGroup;
