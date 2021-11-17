import { Flex, Spacer, VStack } from '@chakra-ui/layout'
import React from 'react'
import { ChakraComponent, Box, BoxProps } from "@chakra-ui/react"
import { HStack, Heading, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"


function Card(props) {
    
    const greeting = 'Project';
    
    return(
        <Flex bg='gray.700' w={800} rounded="lg" m="5" align='center' p={3} justifyContent='space-between'>
        <VStack>
        <HStack>
            <Heading size="md"> {props.title}</Heading>
            <Spacer/>
            <Button>Edit</Button>
        </HStack>
        
        <HStack>
            <Heading size="sm">Product Owner: {props.productowner}</Heading>
            <Heading size="sm">Last opened: {props.lastopened}</Heading>
        </HStack>
        </VStack>
        
        
        
        </Flex>
        );
    }
    
    export default Card
    