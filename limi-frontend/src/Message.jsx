//JSX: JavaScript XML
// This file contains a React component that displays a message.
function Message() {
    // Function implementation goes here

    const name = "Limi";


    return (
        <div>
            <h1>This is {name}'s Website </h1>
            <p>Hello, {name}! Welcome to the Message Component.</p>
        </div>
    );
}

export default Message;