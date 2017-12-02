import React, { Component } from 'react';
import axios from 'axios';
import './App.css';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      url: '',
      error: '',
      hash: ''
    };
  }

  generateLink = () => {       

      if (this.state.url) {
          axios.post(`${process.env.REACT_APP_API_URL}`, {
              url: this.state.url
          })
          .then(res => {                
            this.setState({ 
              hash: `${res.data.hash}`,
              error: ''
            })
          }).catch(error => {            
            this.setState({ error: 'Unable to shorten that link. It is not a valid url.' });  
          })
      }      
  }

  onSubmitHandler = (e) => {
    e.preventDefault();  
    this.generateLink();  
  }

  onChange = () => {
    this.setState({hash: '', error: ''});
  }

  render() {
    return (
      <div className="App">
        
        <header className="App-header">
          <h1 className="App-title">URL Shortener</h1>
        </header>
        
        <form className="shortener-url_form" onSubmit={this.onSubmitHandler} onChange={this.onChange}>
          <input value={this.state.url} onChange={e => this.setState({url: e.target.value})} 
            name="urlField" className="shortener-url_form-input" type="text" placeholder=" Provide your link to shorten "/>
          <button className="shortener-url_form-button" type="submit">
            submit
          </button>
        </form>
        
        {this.state.hash &&
        <p className="App-success">
          <a href={`${window.location.origin}/${this.state.hash}`} target="_blank">
            {`${window.location.origin}/${this.state.hash}`}
          </a>
        </p>
        }
        
        {this.state.error &&
        <p className="App-error">
          {this.state.error}
        </p>
        }
      </div>
    );
  }
}

export default App;
