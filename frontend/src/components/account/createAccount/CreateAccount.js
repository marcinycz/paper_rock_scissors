import React, {Component} from "react";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import './Account.css';
import GameAlert from "../../Alerts/GameAlert";
import Badge from 'react-bootstrap/Badge';


let usernameLogin = localStorage.getItem("username");

class CreateAccount extends Component{
    constructor(props){
        super(props);
        this.alert = React.createRef();
    }
    
    handleCreateAccount = event => {
        event.preventDefault();
        this.createNewUser(event.target.username.value);
    }

    createNewUser(username){
        fetch('http://localhost:8080/user', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
            })
        })
        .then(function(response){
            if (response.status === 200){
                response.json().then((user) => {
                    //user.id, user.username

                     console.log("Add new user" + user.id);
                     this.showAlert("success", "Add new user");
                     window.location.reload(false);
            })
            } else if(response.status === 409){
                console.log("User already exists");
                this.showAlert("danger", "User already exists");
            }else if(response.status === 411){
                console.log("Name too short");
                this.showAlert("danger", "Name too short");
            }
            else if(response.status === 417){
                console.log("Not use space");
                this.showAlert("danger", "Not use space");
            } else{
                console.log("Fail add user");
                this.showAlert("danger", "Fail, did't add new user");
            }
        }.bind(this))
        .catch(function(error) {
            console.log("user error!");
            this.showAlert("danger", "user error");
        }.bind(this));
    }

    showAlert(variant, heading, message, message1) {
        this.alert.current.setVariant(variant);
        this.alert.current.setHeading(heading);
        this.alert.current.setMessage(message);
        this.alert.current.setMessage1(message1);
        this.alert.current.setVisible(true);
    }

    render() {
        if(usernameLogin == null){
            return(
                <div>
                    <Form>
                        <h5>
                            <Badge pill bg="warning" text="dark" size="lg" margin="20px">
                                If you don't have an account, create it!
                            </Badge>{' '}
                        </h5>
                     </Form>
                    <Form onSubmit = { this.handleCreateAccount } >
                        <Form.Group controlId = "username" size = "sm">
                        <Form.Control autoFocus name = "username" placeholder="Username"/>
                        </Form.Group>
        
                        <Button block size = "sm" type = "submit">
                            Create new account
                        </Button>
         
                    </Form>
                    <GameAlert ref={this.alert}/>
            </div>
              )
        }else{
            return(
                <></>
            )  
        }
    }
  }

export default CreateAccount;