import {FormEvent, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../app/hooks";
import {Button} from "../components/Button";
import PasswordInput from "../components/PasswordInput";
import TextInput from "../components/TextInput";
import {registerAndAuthenticateUser} from "../features/auth/authSlice";
import loaderAnimation from '../images/loader.svg';
import {successToast} from "../features/toasts/toastsSlice";

function validateCredentials(credentialName: string, credentialValue: string) {
    if (credentialValue.match(/^[а-яА-Яa-zA-Z0-9_]{5,}$/)) {
        return undefined;
    }

    return `${credentialName} может содержать буквы, цифры и подчеркивания!`;
}

export default function RegisterPage() {
    const authenticated = useAppSelector(state => state.auth.authenticated);
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const [login, setLogin] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [warningText, setWarningText] = useState('');

    if (authenticated) navigate('/home');

    function onSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setWarningText('');
        setLoading(true);

        dispatch(registerAndAuthenticateUser(login, firstName, lastName, password))
            .then((res) => {
                setLoading(false);
                if (res.success) {
                    navigate('/login');
                    dispatch(successToast(`Регистрация прошла успешно!`));
                } else {
                    if (res.message !== undefined)
                        setWarningText(res.message);
                }
            });
    }

    const usernameWarning = validateCredentials('Логин', login);
    const firstNameWarning = validateCredentials('Имя', firstName);
    const lastNameWarning = validateCredentials('Фамилия', lastName);
    const passwordWarning = validateCredentials('Пароль', password);

    const disableButton = loading || usernameWarning !== undefined || passwordWarning !== undefined
        || firstNameWarning !== undefined || lastNameWarning !== undefined;

    return (
        <div className='bg-gray-200 p-3 rounded-md w-full shadow-xl mx-10 md:max-w-xl md:justify-between md:gap-4'>
            <section className="px-5">
                <h1 className='text-3xl font-extrabold mt-2 mb-5'>Регистрация</h1>
                <form onSubmit={onSubmit}>
                    <TextInput
                        id="username"
                        name="username"
                        placeholder='Логин'
                        value={login}
                        onChange={e => setLogin(e.target.value)}
                        warningText={usernameWarning}
                    />
                    <TextInput
                        id="username"
                        name="username"
                        placeholder='Имя'
                        value={firstName}
                        onChange={e => setFirstName(e.target.value)}
                        warningText={firstNameWarning}
                    />
                    <TextInput
                        id="username"
                        name="username"
                        placeholder='Фамилия'
                        value={lastName}
                        onChange={e => setLastName(e.target.value)}
                        warningText={lastNameWarning}
                    />
                    <PasswordInput
                        id="password"
                        name="password"
                        placeholder='Пароль'
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        warningText={passwordWarning}
                    />
                    <p className="text-red-500 text-xs mb-5">{warningText}</p>
                    <Button disabled={disableButton}>{loading ?
                        <img src={loaderAnimation} alt='Loading' className="m-auto max-h-[1em]"/> : 'Зарегистрироваться'}</Button>
                </form>
                <Link to="/login" className='text-xs text-gray-500 text-center block hover:text-violet-500'>
                    У меня уже есть аккаунт!
                </Link>
            </section>
        </div>
    )
}