import { Flex, Spacer, VStack } from '@chakra-ui/layout'
import React from 'react'
import { ChakraComponent, Box, BoxProps } from "@chakra-ui/react"
import { HStack, Heading, Text, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"


function Card(props) {

    const greeting = 'Project';

    return(
        <Flex bg='gray.700' w={800} rounded="lg" m="5" align='center' p={3} justifyContent='space-between'>
        <VStack>
        <HStack>
            <Heading size="md" color="teal"> {props.title}</Heading>
            <Spacer/>
            <Button
                type='submit' bg='teal.400' hover={{bg: 'teal.300'}} w='20'
            >Open</Button>

        </HStack>

        <VStack>
            <Text size="sm" style={{alignSelf: 'flex-start'}}>Product Owner: {props.productowner}</Text>
            <Text size="sm" style={{alignSelf: 'flex-start'}}>Last opened: {props.lastopened}</Text>
        </VStack>
        </VStack>



        </Flex>
        );
    }

    export default Card
