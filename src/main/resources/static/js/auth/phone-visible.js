/* How to use firebase service
 * import { } from 'https://www.gstatic.com/firebasejs/10.12.5/firebase-SERVICE.js'
 * (여기서 SERVICE는 firebase-firestore와 같은 SDK 이름입니다).
* */
import {initializeApp} from "https://www.gstatic.com/firebasejs/10.12.5/firebase-app.js";
import {
    getAuth,
    onAuthStateChanged,
    RecaptchaVerifier,
    signInWithPhoneNumber,
    signOut
} from "https://www.gstatic.com/firebasejs/10.12.5/firebase-auth.js";
import {firebaseConfig} from "../auth/config-firebase.js";

initializeApp(firebaseConfig);

const auth = getAuth();

const grecaptcha = window.grecaptcha;

const accountDetails = document.getElementById('account-details');
const cancelVerifyCodeButton = document.getElementById('cancel-verify-code-button');
const phoneNumberInput = document.getElementById('phone-number');
const signInForm = document.getElementById('sign-in-form');
const signInButton = document.getElementById('sign-in-button');
const signInStatus = document.getElementById('sign-in-status');
const signOutButton = document.getElementById('sign-out-button');
const verificationCodeForm = document.getElementById('verification-code-form');
const verificationCodeInput = document.getElementById('verification-code');
const verifyCodeButton = document.getElementById('verify-code-button');

/*
* 로그인/로그아웃 버튼 클릭 시 호출되는 함수.
*/
function onSignInSubmit(e) {
    e.preventDefault();

    if (isCaptchaOK() && isPhoneNumberValid()) {
        window.signingIn = true;
        updateSignInButtonUI();

        const phoneNumber = getPhoneNumberFromUserInput();
        const appVerifier = window.recaptchaVerifier;

        signInWithPhoneNumber(auth, phoneNumber, appVerifier)
            .then(function (confirmationResult) {
                // SMS sent. Prompt user to type the code from the message
                console.log('인증코드 보냄!!');
                window.confirmationResult = confirmationResult;
                window.signingIn = false;
                updateSignInButtonUI();
                updateVerificationCodeFormUI();
                updateVerifyCodeButtonUI();
                updateSignInFormUI();
            })
            .catch(function (error) {
                // Error; SMS not sent
                console.error('Error during signInWithPhoneNumber', error);
                window.alert(
                    'Error during signInWithPhoneNumber:\n\n' +
                    error.code + '\n\n' +
                    error.message
                );
                window.signingIn = false;
                updateSignInFormUI();
                updateSignInButtonUI();
            });
    }
}

/*
* verify code 버튼 클릭 시 호출되는 함수.
*/
function onVerifyCodeSubmit(e) {
    e.preventDefault();

    const code = getCodeFromUserInput();
    if (code) {
        window.verifyingCode = true;
        updateVerifyCodeButtonUI();

        window.confirmationResult.confirm(code)
            .then(function (result) {
                // User signed in successfully.
                const user = result.user;
                window.verifyingCode = false;
                window.confirmationResult = null;
                updateVerificationCodeFormUI();
            })
            .catch(function (error) {
                // User couldn't sign in (bad verification code?)
                console.error('Error while checking the verification code', error);
                window.alert(
                    'Error while checking the verification code:\n\n' +
                    error.code +
                    '\n\n' +
                    error.message,
                );
                window.verifyingCode = false;
                updateSignInButtonUI();
                updateVerifyCodeButtonUI();
            });
    } else {
        console.error('No confirmationResult available');
        window.verifyingCode = false;
        updateSignInButtonUI();
        updateVerifyCodeButtonUI();
    }
}

/*
* verification code 입력 취소.
*/
function cancelVerification(e) {
    e.preventDefault();
    window.confirmationResult = null;
    updateVerificationCodeFormUI();
    updateSignInFormUI();
}

/*
* 사용자가 로그아웃 버튼 클릭.
*/
function onSignOut() {
    signOut(auth);
}

/*
* 사용자가 입력한 verification code 가져오기.
*/
function getPhoneNumberFromUserInput() {
    return phoneNumberInput.value;
}

/*
* 사용자가 입력한 verification code 가져오기.
*/
function getCodeFromUserInput() {
    return verificationCodeInput.value;
}

/*
* ReCaptcha가 OK 상태일 때 true 반환
*/
function isCaptchaOK() {
    console.log('왔니?');
    console.log(grecaptcha);
    console.log(window.recaptchaWidgetId);
    if (
        typeof grecaptcha !== 'undefined' &&
        window.recaptchaWidgetId !== null
    ) {
        console.log('ture');
        const recaptchaResponse = grecaptcha.getResponse(window.recaptchaWidgetId);
        console.log('recaptchaResponse: '+recaptchaResponse);
        console.log('recaptchaResponse 실제 반환 값: '+Boolean(recaptchaResponse !== ''));
        return Boolean(recaptchaResponse !== '');
    }
    console.log('false');
    return false;
}

/*
* 전화번호가 유효하면 true 반환.
*/
function isPhoneNumberValid() {
    console.log('isPhoneNumberValid 함수 호출!');
    const pattern = /^\+[0-9\s\-\(\)]+$/;
    const phoneNumber = getPhoneNumberFromUserInput();
    console.log('사용자가 입력한 폰번호: ' + phoneNumber);
    return phoneNumber.search(pattern) !== -1;
}


/*
* 로그인 폼 상태 변경.
*/
function updateSignInFormUI() {
    console.log('updateSignInFormUI 함수 호출!!'+'\n currentUser: '+auth.currentUser+'\n confirmationResult: '+window.confirmationResult);
    if (auth.currentUser || window.confirmationResult) {
        signInForm.style.display = 'none';
    } else {
        resetReCaptcha();
        signInForm.style.display = 'block';
    }
}
/*
* ReCaptcha와 값 상태에 따라 로그인 버튼 상태 변경.
*/
function updateSignInButtonUI() {
    console.log('updateSignInButtonUI 함수 호출!!');
    console.log('isCaptchaOk: ' + Boolean(isCaptchaOK()));
    console.log('isPhoneNumberValid: ' + Boolean(isPhoneNumberValid()));
    console.log('window.signingIn: ' + Boolean(window.signingIn));

    signInButton.disabled =
        !isCaptchaOK() || !isPhoneNumberValid() || Boolean(window.signingIn);
}


/*
* verify code form 상태를 변경.
*/
function updateVerificationCodeFormUI() {
    if (!auth.currentUser && window.confirmationResult) {
        verificationCodeForm.style.display = 'block';
    } else {
        verificationCodeForm.style.display = 'none';
    }
}

/*
* 값 상태에 따라 verify code 버튼 상태 변경.
*/
function updateVerifyCodeButtonUI() {
    verifyCodeButton.disabled = !!window.verifyingCode || !getCodeFromUserInput();
}

/*
* ReCaptcha 위젯을 다시 초기화.
*/
function resetReCaptcha() {
    if (
        typeof grecaptcha !== 'undefined' &&
        typeof window.recaptchaWidgetId !== 'undefined'
    ) {
        grecaptcha.reset(window.recaptchaWidgetId);
    }
}

/*
* 로그아웃 버튼 상태 변경.
*/
function updateSignOutButtonUI() {
    if (auth.currentUser) {
        signOutButton.style.display = 'block';
    } else {
        signOutButton.style.display = 'none';
    }
}

/*
* 로그인한 사용자 상태 패널 변경.
*/
function updateSignedInUserStatusUI() {
    const user = auth.currentUser;
    if (user) {
        signInStatus.textContent = 'Signed in';
        accountDetails.textContent = JSON.stringify(user, null, '  ');
    } else {
        signInStatus.textContent = 'Signed out';
        accountDetails.textContent = 'null';
    }
}

/*
* auth 상태 변경 수신.
*/
onAuthStateChanged(auth, (user) => {
    if (user) {
        console.log('auth 상태 user ok!!');
        // User is signed in.
        const uid = user.uid;
        const email = user.email;
        const photoURL = user.photoURL;
        const phoneNumber = user.phoneNumber;
        const isAnonymous = user.isAnonymous;
        const displayName = user.displayName;
        const providerData = user.providerData;
        const emailVerified = user.emailVerified;
    }
    console.log('auth 상태 user not ok!!');
    updateSignInButtonUI();
    updateSignInFormUI();
    updateSignOutButtonUI();
    updateSignedInUserStatusUI();
    updateVerificationCodeFormUI();
});

// Event bindings.
signInForm.addEventListener('submit', onSignInSubmit);
signOutButton.addEventListener('click', onSignOut);
phoneNumberInput.addEventListener('click', updateSignInButtonUI);
phoneNumberInput.addEventListener('change', updateSignInButtonUI)
verificationCodeInput.addEventListener('click', updateVerifyCodeButtonUI);
verificationCodeInput.addEventListener('change', updateVerifyCodeButtonUI);
verificationCodeForm.addEventListener('submit', onVerifyCodeSubmit);
cancelVerifyCodeButton.addEventListener('click', cancelVerification);


window.recaptchaVerifier = new RecaptchaVerifier(auth, 'recaptcha-container', {
    size: 'normal',
    'callback': (_response) => {
        // reCAPTCHA solved, allow signInWithPhoneNumber.
        updateSignInButtonUI();
    },
    'expired-callback': () => {
        // Response expired. Ask user to solve reCAPTCHA again.
        updateSignInButtonUI();
    }
});

window.recaptchaVerifier.render().then(function (widgetId) {
    window.recaptchaWidgetId = widgetId;
});