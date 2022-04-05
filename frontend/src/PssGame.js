import React, {Component} from 'react';
import './App.css';
import { Form } from 'react-bootstrap';
import { Button } from 'react-bootstrap';

import './PssGame.css'
import GameAlert from './GameAlert';

class PssGame extends Component{
    constructor(props){
        super(props);
        this.gameAlert = React.createRef();
    }


    handleSubmit = event => {
        event.preventDefault();
        this.sendMyChoiceInt(event.target.myChoiceInt.value);
    }
    
    sendMyChoiceInt(myChoiceInt){
        fetch('http://localhost:8080/pss', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                myChoiceInt: myChoiceInt,
            })
        }).then(function(response){
            if (response.status === 200){
                console.log("Add game");
                //this.showGameAlert("succes", "Add new game");
            } else{
                console.log("Fail add game");
                //this.showGameAlert("dangrer", "Fail, did't add new game");
            }
        }.bind(this)).catch(function(error) {
            console.log("error!");
            //this.showGameAlert("dangrer", "Error");
        }.bind(this));
    }

    showGameAlert(variant, heading, message) {
        this.registrationAlert.current.setVariant(variant);
        this.registrationAlert.current.setHeading(heading);
        this.registrationAlert.current.setMessage(message);
        this.registrationAlert.current.setVisible(true);
    }

  render() {
    return (
    <>
    <div className='PssGame' >
        <Form onSubmit = {this.handleSubmit}>
            <Form.Group controlId='myChoiceInt' size="lg">
                <Form.Label>Your choice</Form.Label>
                <Form.Control autoFocus name="myChoiceInt"/>
            </Form.Group>
            <Button size = "lg" variant="primary" type="submit">Paper</Button>{' '}
        </Form>
        
        <Form>
            <Button variant="secondary">Stone</Button>{' '}
        </Form>

        <Form>
            <Button variant="success">Scissors</Button>{' '}
        </Form>
    </div>

    <GameAlert ref={this.RegistrationAlert}/>
    </>
    )
  } 
}

export default PssGame;
