import React, {Component} from 'react';
import './App.css';
import { Form } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import { Navbar } from 'react-bootstrap';
import { Nav } from 'react-bootstrap';
import { Container } from 'react-bootstrap';
import Badge from 'react-bootstrap/Badge'



import './PssGame.css'
import GameAlert from './GameAlert';
import HistoryAlert from './HistoryAlert';

class PssGame extends Component{

    constructor(props){
        super(props);
        this.gameAlert = React.createRef();
        this.historyAlert = React.createRef();
    }


    handleChoiceSumbit = event => {
        event.preventDefault();
        this.sendMyChoiceInt(event.target.value);
    }

    handleDeleteSumbit = event => {
        event.preventDefault();
        this.deleteHistory();
    }

    handleHistory = event => {
        event.preventDefault();
        this.getLastHistory();
    }
    
    sendMyChoiceInt(myChoiceInt){
        fetch('http://localhost:8080/prs', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                myChoiceInt: myChoiceInt,
            })
        })
        .then(function(response){
            if (response.status === 200){
                response.json().then((pss) => {
                    //pss.id, pss.myChoiceInt, pss.computerChoice, pss.myChoice, pss.score, pss.verdict, pss.totalScore

                     console.log("Add new game");
                    
                     if(pss.score === 1){
                        this.showGameAlert("success", pss.verdict, pss.myChoice +  " vs " + pss.computerChoice , "SCORE: " + pss.totalScore);
                    } else if (pss.score === 0){
                        this.showGameAlert("warning", pss.verdict, pss.myChoice +  " vs " + pss.computerChoice , "SCORE: " + pss.totalScore);
                    } else{
                        this.showGameAlert("danger", pss.verdict, pss.myChoice +  " vs " + pss.computerChoice , "SCORE: " + pss.totalScore);
                    }
                    //Show history
                    this.getLastHistory();
            })
            } else{
                console.log("Fail add game");
                this.showGameAlert("danger", "Fail, did't add new game");
            }
        }.bind(this))
        .catch(function(error) {
            console.log("error!");
            this.showGameAlert("danger", "Error");
        }.bind(this));
    }

    getLastHistory(){
        fetch('http://localhost:8080/prsLastGames', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        })
        .then(function(response){
            if (response.status === 200){
                response.json().then((pss) => {
                    //pss.id, pss.myChoiceInt, pss.computerChoice, pss.myChoice, pss.score, pss.verdict, pss.totalScore
                    console.log("Get last history");

                    let jsonLength = Object.keys(pss).length;

                    let historyMessage = ["","","","",""];

                    for(let i = 0; i < jsonLength; i++){
                        historyMessage[i] = i+1 + ". " + pss[i].myChoice + " vs " + pss[i].computerChoice + " => " + pss[i].verdict + " |  SCORE: " + pss[i].totalScore;
                    }

                     this.showHistoryAlert("primary", "HISTORY",
                     historyMessage[0],
                     historyMessage[1],
                     historyMessage[2],
                     historyMessage[3],
                     historyMessage[4]
                     );

            })
            } else{
                console.log("Fail add game");
                this.showGameAlert("danger", "Fail, did't add new game");
            }
        }.bind(this))
        .catch(function(error) {
            console.log("error!");
            this.showGameAlert("danger", "Error");
        }.bind(this));
    }

    deleteHistory(){
        fetch('http://localhost:8080/prsDeleteHitory', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({

            })
        }).then(function(response){
            if (response.status === 200){
                console.log("Delete game history");
                this.showGameAlert("success", "Let's play", " ", "Deleted game history");
                this.getLastHistory();
            } else{
                console.log("Fail delete game history");
                this.showGameAlert("danger", "Fail, did't delete game history");
            }
        }.bind(this)).catch(function(error) {
            console.log("error!");
            this.showGameAlert("danger", "Error");
        }.bind(this));
    }

    showGameAlert(variant, heading, message, message1) {
        this.gameAlert.current.setVariant(variant);
        this.gameAlert.current.setHeading(heading);
        this.gameAlert.current.setMessage(message);
        this.gameAlert.current.setMessage1(message1);
        this.gameAlert.current.setVisible(true);
    }

    showHistoryAlert(variant, heading, message0, message1, message2, message3, message4) {
        this.historyAlert.current.setVariant(variant);
        this.historyAlert.current.setHeading(heading);
        this.historyAlert.current.setMessage0(message0);
        this.historyAlert.current.setMessage1(message1);
        this.historyAlert.current.setMessage2(message2);
        this.historyAlert.current.setMessage3(message3);
        this.historyAlert.current.setMessage4(message4);
        this.historyAlert.current.setVisible(true);
    }

  render() {
    return (
    <>
    <div >
        <Navbar  bg="dark" variant="dark">
            <Container>
                <Navbar.Brand className="m-auto" href="#home">
                        PAPER ROCK SCISSORS
                </Navbar.Brand>
                <Nav className="m-auto">
                    <Nav.Link href="">Home</Nav.Link>
                    <Nav.Link onClick={this.handleDeleteSumbit}>New game</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    </div>

    <div className='PssGame' >
        <Form>
        <h3>
            <Badge pill bg="warning" text="dark" size="lg" margin="20px">
                THE CHOICE IS YOURS!
            </Badge>{' '}
        </h3>
        </Form>
        <Form >
            <Form.Group controlId='myChoiceInt' size="lg">
                <Button onClick={this.handleChoiceSumbit} size="lg" variant="primary" value={0}>Paper</Button>{' '}
                <Button onClick={this.handleChoiceSumbit} size="lg" variant="secondary"   value={1}>Rock</Button>{' '}
                <Button onClick={this.handleChoiceSumbit} size="lg" variant="success"   value={2}>Scissors</Button>{' '}
            </Form.Group>
        </Form>

        <GameAlert ref={this.gameAlert}/>
        <HistoryAlert ref={this.historyAlert}/>

        <Form onSubmit = {this.handleDeleteSumbit}>
            <Form.Group controlId='deleteHistory' size="lg">
                <Button size="lg" variant="danger" type="submit">Reset game</Button>{' '}
            </Form.Group>
        </Form>
    </div>
    </>
    )
  } 
}

export default PssGame;
