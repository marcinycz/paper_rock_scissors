import React, {Component} from 'react';
import './App.css';
import { Form } from 'react-bootstrap';
import { Button } from 'react-bootstrap';

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
        this.sendMyChoiceInt(event.target.myChoiceInt.value);
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
        fetch('http://localhost:8080/pss', {
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
        fetch('http://localhost:8080/pssLastGames', {
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
                     console.log(jsonLength);

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
        fetch('http://localhost:8080/pssDeleteHitory', {
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
    <div className='PssGame' >
        <Form onSubmit = {this.handleChoiceSumbit}>
            <Form.Group controlId='myChoiceInt' size="lg">
                <Button size="lg" variant="primary" type="submit" name="myChoiceInt" value={0}>Paper</Button>{' '}
            </Form.Group>
        </Form>
        <Form onSubmit = {this.handleChoiceSumbit}>
            <Form.Group controlId='myChoiceInt' size="lg">
                <Button size="lg" variant="secondary" type="submit" name="myChoiceInt" value={1}>Stone</Button>{' '}
            </Form.Group>
        </Form>
        <Form onSubmit = {this.handleChoiceSumbit}>
            <Form.Group controlId='myChoiceInt' size="lg">
                <Button size="lg" variant="success" type="submit" name="myChoiceInt" value={2}>Scissors</Button>{' '}
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
