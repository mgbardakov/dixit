import {
    Link,
    useNavigate
} from "react-router-dom";
import { Center, Input, Button } from '@chakra-ui/react'
import './Auth.scss'
import { useEffect, useState, SyntheticEvent } from "react";
import AuthModal from '../../components/AuthModal/AuthModal'




type Nullable<T> = T | null;

const Auth: React.FC = () => {
    let navigate = useNavigate();
    const [name, setName] = useState<string>('')
    const [hasName, setHasName] = useState<boolean>(false)
    const [modalOpen, setModalOpen] = useState<boolean>(false)


    const gameID = 'sdfaaaw'

    useEffect(() => {
        nameHandler()
    }, [])

    function nameHandler(): void {
        let name: Nullable<string> = localStorage.getItem('playerName')
        if (name) {
            setHasName(true)
            setName(name)
        }
    }

    const nameSubmit = () => {
        localStorage.setItem('playerName', name)
        setName(name)
        setHasName(true)

    }
    const createGame = () => {
        navigate("lobby/assdsfg");
    }

    const openModal = () => {
        setModalOpen(true)
    }
    const authBlock: JSX.Element = <div className="auth__block">
        <Input placeholder='Введите имя' onChange={(e) => { setName(e.target.value) }} />
        <Button mt={4} w={'100%'} colorScheme='blue' onClick={nameSubmit}>Подтвердить</Button>
    </div>

    const hasNameBlock: JSX.Element = <div className="auth__block">
        <p>Добро пожаловать, {name}</p>
        <Button mt={4} w={'100%'} colorScheme='blue' onClick={createGame}>Создать игру</Button>
        <Button mt={4} w={'100%'} colorScheme='blue' onClick={openModal}>Войти в существующую</Button>
    </div>


    return (
        <Center h='100vh' >
            <div className="auth">
                {hasName ? hasNameBlock : authBlock}
            </div>
            <AuthModal
                isOpen={modalOpen}
                close={() => { setModalOpen(false) }}
            />
        </Center>
    )
}

export default Auth