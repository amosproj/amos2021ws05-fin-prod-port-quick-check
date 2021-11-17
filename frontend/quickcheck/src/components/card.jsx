import { Flex, Spacer, VStack } from '@chakra-ui/layout'
import React from 'react'
import { ChakraComponent, Box, BoxProps } from "@chakra-ui/react"
import { HStack, Heading, Stack,  Text, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"


function Card(props) {

    const greeting = 'Project';

    return(
        <Flex bg='gray.700'  rounded="lg" m="5" align='center' p={3} justifyContent='space-between'>


            <Heading size="lg" color="teal"> {props.title}</Heading>

            <Spacer/>
            <Button
                type='submit' bg='teal.400' hover={{bg: 'teal.300'}} w='20' style={{alignSelf: 'flex-right'}}
            >Open</Button>

                    <Box p={6}>
                      <Stack spacing={0} align={'center'} mb={5}>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                          {props.productowner}
                        </Heading>
                        <Text color={'gray.500'}>Product Owner</Text>
                      </Stack>

                      <Stack direction={'row'} justify={'center'} spacing={6}>
                        <Stack spacing={0} align={'center'}>
                          <Text fontWeight={600}>{props.created}</Text>
                          <Text fontSize={'sm'} color={'gray.500'}>
                            Created
                          </Text>
                        </Stack>
                        <Stack spacing={0} align={'center'}>
                          <Text fontWeight={600}>{props.lastopened}</Text>
                          <Text fontSize={'sm'} color={'gray.500'}>
                            Last Opened
                          </Text>
                        </Stack>
                      </Stack>
                      </Box >
                      </Flex>

        );
    }

    export default Card
