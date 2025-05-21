import { Fragment } from "react";

function ListGroup() {
    let items = ["An item", "A second item", "A third item", "A fourth item", "And a fifth one"];
    items = [];

    if (items.length === 0) {
        return <p>No items found</p>;
    }

    
  return (
    <Fragment>
        <h1>List</h1>
        {item.length === 0 &&  <p>No items found</p>}
      <ul className="list-group">
      {items.map((item, index) => <li className="list-group-item" key={item} onClick={() => console.log(item,index)}>{item}</li>)}
      </ul>
    </Fragment>
  );
}

export default ListGroup;
