import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import './App.css';
import { findAllElectronics } from './api';
import ElectronicList from './pages/ElectronicList';
import { setElectronics } from './reducers/electronics-reducer';

function App() {

  const dispatch = useDispatch();

  useEffect(() => {

    findAllElectronics()
      .then(response => dispatch(setElectronics(response.data)))
      .catch(error => console.error('Error fetching electronics data:', error));

  }, []);

  return (
    <div>
      <header>
        <h1>Energy Consumption Simulator</h1>
      </header>
      <div className="container">
        <div className="column">
          <ElectronicList />
        </div>
      </div>
    </div>
  );
}

export default App;
