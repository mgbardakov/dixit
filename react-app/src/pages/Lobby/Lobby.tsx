import { Center, List, ListItem, ListIcon, Button } from '@chakra-ui/react'
import { CheckCircleIcon } from '@chakra-ui/icons'
import {
    Link,
    useNavigate,
    useParams
} from "react-router-dom";


const Lobby: React.FC = () => {
    let navigate = useNavigate();
    let { gameID } = useParams();


    return (
        <Center h='100vh' >
            <div className="lobby__content">
                <p >Ваш gameID: {gameID}</p>
                <List spacing={3} mt={10}>
                    <ListItem>
                        <ListIcon as={CheckCircleIcon} color='green.500' />
                        Дима
                    </ListItem>
                    <ListItem>
                        <ListIcon as={CheckCircleIcon} color='green.500' />
                        Петя
                    </ListItem>
                    <ListItem>
                        <ListIcon as={CheckCircleIcon} color='green.500' />
                        Вася
                    </ListItem>
                </List>
                <Button mt={4} w={'100%'} colorScheme='blue' onClick={() => { navigate('../game/ddd') }}>Начать игру</Button>
            </div>
        </Center>)
}

export default Lobby