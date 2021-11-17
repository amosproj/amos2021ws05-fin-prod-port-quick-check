import { Flex, Spacer } from '@chakra-ui/layout'
import { Heading, Avatar } from '@chakra-ui/react'
import React from 'react'

function Menubar(props) {
    return (
        <Flex bg='teal' w='full' align='center' p={3} justifyContent={'space-between'}>

            <Flex align='center' maxW={600}>
                <Spacer />
                <Heading size="xl">{props.title}</Heading>
                <Spacer />
                <Avatar></Avatar>
            </Flex>
        </Flex>
    )
}

export default Menubar

