// TODO: Replace the following with your app's Firebase project configuration
export const firebaseConfig = {
    apiKey: "",
    authDomain: "",
    projectId: "",
    storageBucket: "",
    messagingSenderId: "",
    appId: "",
    measurementId: ""
};

await loadConfig();

async function loadConfig() {
    try {
        const response = await fetch('/api/firebase/config');
        const config = await response.json();

        firebaseConfig.apiKey = config.apiKey;
        firebaseConfig.authDomain = config.authDomain;
        firebaseConfig.projectId = config.projectId;
        firebaseConfig.storageBucket = config.storageBucket;
        firebaseConfig.messagingSenderId = config.messagingSenderId;
        firebaseConfig.appId = config.appId;
        firebaseConfig.measurementId = config.measurementId;

        console.log('프로젝트 ID: ' + firebaseConfig.projectId);
    } catch (error) {
        console.error('Failed to load config: ' + error);
    }
}
