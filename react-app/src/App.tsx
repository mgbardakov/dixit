import React from 'react';
import { Dixit, DixitPage } from './pages/Dixit/Dixit';
import Auth from './pages/Auth/Auth';
import Lobby from './pages/Lobby/Lobby';
import Page404 from './pages/Page404/Page404';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";





function App() {


  return (


    <Router>

      <div>
        <Routes>
          <Route path="/" element={<Auth />}>
          </Route>
          <Route path="game/:gameID" element={<Dixit />}>
          </Route>
          <Route path="lobby/:gameID" element={<Lobby />}>
          </Route>
          <Route path="*" element={<Page404 />}>
          </Route>
        </Routes>
      </div>

    </Router>
  );
}

export default App;
