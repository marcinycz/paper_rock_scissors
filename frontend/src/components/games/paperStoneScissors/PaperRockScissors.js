import React, {Component} from 'react';

import { Form } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import Badge from 'react-bootstrap/Badge';
import './PaperRockScissors.css';
import HistoryAlert from '../../Alerts/HistoryAlert';
import GameAlert from '../../Alerts/GameAlert';

let usernameLogin = localStorage.getItem("username");
let idLogin = 0; 
let lastTotalScore;

class PaperRockScissors extends Component{
    constructor(props){
        super(props);
        
        this.gameAlert = React.createRef();
        this.gameAlertScore = React.createRef();
        this.historyAlert = React.createRef();
        
        this.getUser();
        setTimeout(() => {  this.getLastHistory(); }, 250);
        setTimeout(() => {  this.showAlertLastScore(lastTotalScore); }, 450);
    }

    showAlertLastScore(score){
        if(score > 0){
            this.showGameAlertScore("success", score);
        } else if (score === 0){
            this.showGameAlertScore("warning", score);
        }  else if (score == null){
            this.showGameAlertScore("warning", 0);
        } else{
            this.showGameAlertScore("danger", score);
        }
    }

    handleChoiceSumbit = event => {
        event.preventDefault();
        if(idLogin !== 0){
            this.sendMyChoiceInt(event.target.value);
        }else{
            this.showGameAlert("danger", "Must be logged ");
        }
    }

    handleDeleteSumbit = event => {
        event.preventDefault();
        if(idLogin !== 0){
            this.deleteHistory();
        }else{
            this.showGameAlert("danger", "Must be logged ");
        }  
    }

    handleHistory = event => {
        event.preventDefault();
        this.getLastHistory();
    }

    getUser(){
        fetch(`http://localhost:8080/user/${usernameLogin}`,{
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        })
        .then(function(response){
            if (response.status === 200){
                response.json().then((user) => {
                    //user.id, user.username
                     console.log("User " + user.id + " " +  user.username);
                     idLogin = user.id;
            })
            } else{
                console.log("Not logged");
                this.showGameAlert("danger", "Not logged");
            }
        }.bind(this))
        .catch(function(error) {
            console.log("error user!");
            this.showGameAlert("danger", "Error user");
        }.bind(this));
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
                idUser: idLogin
            })
        })
        .then(function(response){
            if (response.status === 200){
                response.json().then((paperStoneScissors) => {
                    //paperStoneScissors.id, paperStoneScissors.myChoiceInt, paperStoneScissors.computerChoice, paperStoneScissors.myChoice, paperStoneScissors.score, paperStoneScissors.verdict, paperStoneScissors.totalScore

                     console.log("Add new game");

                     this.showAlertLastScore(paperStoneScissors.totalScore);

                     if(paperStoneScissors.score === 1){
                        this.showGameAlert("success", paperStoneScissors.verdict, paperStoneScissors.myChoice +  " vs " + paperStoneScissors.computerChoice);
                    } else if (paperStoneScissors.score === 0){
                        this.showGameAlert("warning", paperStoneScissors.verdict, paperStoneScissors.myChoice +  " vs " + paperStoneScissors.computerChoice);
                    } else{
                        this.showGameAlert("danger", paperStoneScissors.verdict, paperStoneScissors.myChoice +  " vs " + paperStoneScissors.computerChoice);
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
        fetch(`http://localhost:8080/prsLastGames/${idLogin}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        })
        .then(function(response){
            if (response.status === 200){
                response.json().then((paperStoneScissors) => {
                    //paperStoneScissors.id, paperStoneScissors.myChoiceInt, paperStoneScissors.computerChoice, paperStoneScissors.myChoice, paperStoneScissors.score, paperStoneScissors.verdict, paperStoneScissors.totalScore
                    console.log("Get last history");
                    
                    let jsonLength = Object.keys(paperStoneScissors).length;

                    if(jsonLength > 0){
                        if(paperStoneScissors !== null){
                            lastTotalScore = paperStoneScissors[0].totalScore;
                        }
                    }

                    let historyMessage = ["","","","",""];

                    for(let i = 0; i < jsonLength; i++){
                        historyMessage[i] = i+1 + ". " + paperStoneScissors[i].myChoice + " vs " + paperStoneScissors[i].computerChoice + " => " + paperStoneScissors[i].verdict + " |  SCORE: " + paperStoneScissors[i].totalScore;
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
                console.log("Fail show history");
                this.showGameAlert("danger", "Fail, show history");
            }
        }.bind(this))
        .catch(function(error) {
            console.log("error!");
            this.showGameAlert("danger", "Error");
        }.bind(this));
    }

    deleteHistory(){
        fetch(`http://localhost:8080/prsDeleteHitory/${idLogin}`, {
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
                this.showGameAlertScore("warning", "0");
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

    showGameAlertScore(variant, heading, message, message1) {
        this.gameAlertScore.current.setVariant(variant);
        this.gameAlertScore.current.setHeading(heading);
        this.gameAlertScore.current.setMessage(message);
        this.gameAlertScore.current.setMessage1(message1);
        this.gameAlertScore.current.setVisible(true);
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
    if(usernameLogin != null){
        return (
            <>
            <div className='PaperRockScissors' >

                <GameAlert ref={this.gameAlertScore}/>
                <Form>
                <h3>
                    <Badge pill bg="warning" text="dark" size="lg" margin="20px">
                        {usernameLogin} THE CHOICE IS YOURS! 
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
                <Form></Form>
        
                <GameAlert ref={this.gameAlert}/>
        
                <Form onSubmit = {this.handleDeleteSumbit}>
                    <Form.Group controlId='deleteHistory' size="lg">
                        <Button size="lg" variant="danger" type="submit">Reset game history</Button>{' '}
                    </Form.Group>
                </Form>
        
                <HistoryAlert ref={this.historyAlert}/>
            </div>
            </>
         )    
    }else{
        return(
            <></>
            )
        }
     }
    }

export default PaperRockScissors;
