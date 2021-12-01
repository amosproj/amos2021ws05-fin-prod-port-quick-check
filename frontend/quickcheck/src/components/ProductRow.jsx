import { HStack, Box, Wrap, Text, IconButton} from '@chakra-ui/react'
import React from 'react'
import { DeleteIcon, AddIcon } from '@chakra-ui/icons';

export default function ProductRow(tag) {
    return (
        <div>
            <Box w="full"
                minW="15em"
                bg="gray.400"
                p={3}
                mb={6}
                align="center"
                rounded="md"
                boxShadow="md"
                overflow="hidden"
                _hover={{ boxShadow: '2xl' }}>
                <HStack>
                    <Text> {tag.product.productName} </Text>
                    <IconButton
                        icon={<DeleteIcon />} 
                        colorScheme='teal'
                        variant='outline'                       
                        size="md"
                        color="white"
                        bg="red.700"
                        w={10}
                    />
                </HStack>

            </Box>
        </div >
    )
}

