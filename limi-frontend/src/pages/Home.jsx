import React from 'react';
import ListGroup from '../components/ListGroup';

function Home() {
  const books = [
    { title: "Percy Jackson & the Olympians: The Lightning Thief", author: "Rick Riordan" },
    { title: "The Mortal Instruments: City of Bones", author: "Cassandra Clare" },
    { title: "The Chronicles of Narnia: The Lion, the Witch and the Wardrobe", author: "C.S. Lewis" },
    { title: "Harry Potter and the Sorcerer's Stone", author: "J.K. Rowling" },
    { title: "Heartstopper", author: "Alice Oseman" },
    { title: "Cemetery Boys", author: "Aiden Thomas" },
    { title: "Fazendo Meu Filme 1: A Estreia de Fani", author: "Paula Pimenta" },
    { title: "Terra Sonâmbula", author: "Mia Couto" },
    { title: "The Kane Chronicles: The Red Pyramid", author: "Rick Riordan" },
    { title: "Magnus Chase and the Gods of Asgard: The Sword of Summer", author: "Rick Riordan" },
    { title: "The Trials of Apollo: The Hidden Oracle", author: "Rick Riordan" },
    { title: "The Hunger Games", author: "Suzanne Collins" },
    { title: "To All the Boys I've Loved Before", author: "Jenny Han" },
    { title: "Six of Crows", author: "Leigh Bardugo" },
    { title: "The Cruel Prince", author: "Holly Black" },
    { title: "Children of Blood and Bone", author: "Tomi Adeyemi" },
    { title: "A Court of Thorns and Roses", author: "Sarah J. Maas" },
    { title: "The Maze Runner", author: "James Dashner" },
    { title: "Shadow and Bone", author: "Leigh Bardugo" },
    { title: "An Ember in the Ashes", author: "Sabaa Tahir" },
    { title: "The Giver", author: "Lois Lowry" },
    { title: "Miss Peregrine's Home for Peculiar Children", author: "Ransom Riggs" },
    { title: "Eleanor & Park", author: "Rainbow Rowell" }
  ];

  const handleSelectItem = (item) => {
    console.log(item);
  };

  return (
    <div>
      <ListGroup
        items={books}
        heading="Catálogo"
        onSelectItem={handleSelectItem}
      />
    </div>
  );
}

export default Home;
