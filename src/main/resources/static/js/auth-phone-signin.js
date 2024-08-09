import {initializeApp} from "https://www.gstatic.com/firebasejs/10.12.5/firebase-app.js";
import {
    getAuth,
    RecaptchaVerifier,
    signInWithPhoneNumber
} from "https://www.gstatic.com/firebasejs/10.12.5/firebase-auth.js";
import {firebaseConfig} from "./init-firebase.js";

initializeApp(firebaseConfig);

const auth = getAuth();

// FIXME 전화번호 인증 코드 확인 및 reCAPTCHA 흐름 고려...
window.recaptchaVerifier = new RecaptchaVerifier(auth, 'recaptcha-container', {});
export function getPhoneNumberFromUserInput() {
    const phoneNumber = document.getElementById("floatingPhoneNumber").value;
    const appVerifier = window.recaptchaVerifier;

    console.log('전화번호 입력 값: ' + phoneNumber);
    console.log('auth : ' + firebaseConfig);


    // 전화번호 인증 요청
    signInWithPhoneNumber(auth, "+82"+phoneNumber, appVerifier)
        .then((confirmationResult) => {
            console.log("전화번호 인증 요청 성공!");
            // SMS sent. Prompt user to type the code from the message, then sign the
            // user in with confirmationResult.confirm(code).
            window.confirmationResult = confirmationResult;
            // ...
        }).catch((error) => {
        // Error; SMS not sent
        console.error('Error during signInWithPhoneNumber:', error);
    });
}