import styles from './Home.module.css';

function HomePage(props) {
      
    return (
        <div className={styles['home-container']}>
            <h1 className={styles.dislinkt}>Dislinkt</h1>
            <h3 className="mt-3">Svi na jednom mestu.</h3>
        </div>
    )
}

export default HomePage;