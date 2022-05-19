import React, {Component} from 'react'
import Alert from 'react-bootstrap/Alert'

import './HistoryAlert.css'

class HistoryAlert extends Component{
    constructor(props){
        super(props)

        this.state = {
            visible: this.props.visible,
            variant: this.props.variant,
            heading: this.props.heading,
            message0: this.props.message0,
            message1: this.props.message1,
            message2: this.props.message2,
            message3: this.props.message3,
            message4: this.props.message4
        };
    }

    setMessage0 = (message0) => {
        this.setState({message0: message0});
      }

      setMessage1 = (message1) => {
        this.setState({message1: message1});
      }

      setMessage2 = (message2) => {
        this.setState({message2: message2});
      }

      setMessage3 = (message3) => {
        this.setState({message3: message3});
      }

      setMessage4 = (message4) => {
        this.setState({message4: message4});
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
          <div className="HistoryAlert">
            <Alert variant={this.state.variant} onClose={() => this.setState({visible: false})}>
                <Alert.Heading>{this.state.heading}</Alert.Heading>
                 <p>
                    {this.state.message0}
                </p>
                <hr />
                <p>
                    {this.state.message1}
                </p>
                <hr />
                <p>
                    {this.state.message2}
                </p>
                <hr />
                <p>
                    {this.state.message3}
                </p>
                <hr />
                <p>
                    {this.state.message4}
                </p>
            </Alert>
          </div>
          </>
        );
      }
      return null;
    }
}

export default HistoryAlert;