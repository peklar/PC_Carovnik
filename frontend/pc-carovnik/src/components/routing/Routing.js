import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './Login';
import Register from './Register';
import Maintainer from './Maintainer';
import Configuration from "./Configuration";
import Components from './Components';

const Routing = () => {
  return (
    <Router>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <Route path="/maintainer" component={Maintainer} />
        <Route path="/configuration" component={Configuration} />
        <Route path="/components" component={Components} />
        {/*  TUKAJ DODAMO DRUGE ROUTE */}
      </Switch>
    </Router>
  );
};

export default Routing;
