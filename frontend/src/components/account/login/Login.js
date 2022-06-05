import React, {Component} from "react";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import Badge from 'react-bootstrap/Badge';

import GameAlert from "../../Alerts/GameAlert";


let usernameLogin = localStorage.getItem("username");

class Login extends Component{
    constructor(props){
        super(props);
        this.alert = React.createRef();
    }
    
    handleLogin = event => {
        event.preventDefault();
        this.loginUser(event.target.username.value);
    }

    loginUser(username){
        fetch('http://localhost:8080/login', {
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

                     console.log("Login succes " + user.id + " " +  user.username);
                     this.showAlert("success", "Login succes");
                     //localStorage.setItem("id", user.id);

                     localStorage.setItem("username", user.username);
                     window.location.reload(false);
            })
            } else if(response.status === 404){
                console.log("User not exists");
                this.showAlert("danger", "User not exists");
            } else if(response.status === 411){
                console.log("Name too short");
                this.showAlert("danger", "Name too short");
            }else{
                console.log("Fail log in");
                this.showAlert("danger", "Fail, did't log in");
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
                <div >
                    <Form>
                        <h2>
                            <Badge pill bg="success" text="dark" size="lg" margin="20px">
                                Login and play!
                            </Badge>
                        </h2>
                     </Form>
                    <Form onSubmit = { this.handleLogin } >
                        <Form.Group controlId = "username" size = "lg">
                        <Form.Control autoFocus name = "username" placeholder="Username"/>
                        </Form.Group>

                        <Button block size = "lg" type = "submit" variant="success">
                            Login
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

export default Login;