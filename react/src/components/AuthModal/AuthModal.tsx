import * as React from 'react';
import {
    Link,
    useNavigate
} from "react-router-dom";

import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    Button,
    Input
} from '@chakra-ui/react'


type ThisProps = {
    isOpen: boolean,
    close: () => void
}

const AuthModal: React.FC<ThisProps> = ({ isOpen, close }) => {
    const [input, setInput] = React.useState<string>('')

    const nav = useNavigate();

    const go = () => {
        nav(`../lobby/${input}`)
    }

    return (
        <div className="AuthModal">

            <Modal isOpen={isOpen} onClose={close}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Введите gameID</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <Input placeholder='gameID' onChange={(e) => { setInput(e.target.value) }} />
                    </ModalBody>

                    <ModalFooter>
                        <Button colorScheme='blue' onClick={go}>Войти</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </div>

    )
}

export default AuthModal