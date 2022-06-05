import React, {Component} from 'react'
import Alert from 'react-bootstrap/Alert'

import './GameAlert.css'

class GameAlert extends Component{
    constructor(props){
        super(props)

        this.state = {
            visible: this.props.visible,
            variant: this.props.variant,
            heading: this.props.heading,
            message: this.props.message,
            message1: this.props.message1
        };
    }

    setMessage = (message) => {
        this.setState({message: message});
      }

      setMessage1 = (message1) => {
        this.setState({message1: message1});
      }

      setHeading = (heading) => {
        this.setState({heading: heading});
      }

      setVariant = (variant) => {
        this.setState({variant: variant})
      }

      setVisible = (isVisible) => {
          this.setState({visible: isVisible});
      }

      render() {
        if (this.state.visible) {
        return (
          <>
          <div className="GameAlert">
            <Alert variant={this.state.variant} onClose={() => this.setState({visible: false})} >
                <Alert.Heading>{this.state.heading}</Alert.Heading>
                    {this.state.message}
            </Alert>
          </div>
          </>
        );
      }
      return null;
    }
}

export default GameAlert;