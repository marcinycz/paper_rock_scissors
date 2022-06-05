import React, {Component} from 'react';
import './App.css';


import CreateAccount from './components/account/createAccount/CreateAccount';
import Login from './components/account/login/Login';
import PaperRockScissors from './components/games/paperStoneScissors/PaperRockScissors';

import { Navbar } from 'react-bootstrap';
import { Nav } from 'react-bootstrap';
import { Container } from 'react-bootstrap';

class App extends Component{
  constructor(props){
    super(props);
    this.alert = React.createRef();
}

logout = event => {
    localStorage.clear();
    window.location.reload(false);
}

  render() {
    return(
    <>
      

          <Navbar  bg="dark" variant="dark">
                  <Container>
                      <Navbar.Brand className="m-auto" href=" ">
                              PAPER ROCK SCISSORS
                      </Navbar.Brand>
                      <Nav className="m-auto">
                          <Nav.Link onClick={this.logout}>Logout</Nav.Link>
                      </Nav>
                  </Container>
          </Navbar>
      <Login/>
      <CreateAccount/>
      <PaperRockScissors/>
    </>
    )
  }
}

export default App;
