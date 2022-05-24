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
// @ts-ignore
import SockJsClient from 'react-stomp';



function App() {


  return (


    <Router>
      <SockJsClient url='http://localhost:8080/gs-guide-websocket?userName=asd' topics={['/topics/all']}
        onMessage={(msg: any) => { console.log(msg); }}
      />
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
