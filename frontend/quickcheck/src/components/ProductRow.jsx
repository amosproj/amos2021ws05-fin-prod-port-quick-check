import { HStack, Box, Wrap, Text, IconButton } from '@chakra-ui/react';
import React from 'react';
import { DeleteIcon, AddIcon } from '@chakra-ui/icons';
import { useEffect } from 'react';

export default function ProductRow({ product, productsData, childToParent, editable }) {

    const removeProduct = (product) => {
        const newProductsData = productsData.filter((p) => p.productName !== product.productName);
        childToParent(newProductsData);

    }



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
                        <Text> {product.productName} </Text>
                        if (editable) {
                            <IconButton
                                icon={<DeleteIcon />}
                                onClick={() => { removeProduct(product) }}
                                colorScheme='teal'
                                variant='outline'
                                size="md"
                                color="white"
                                bg="red.700"
                                w={10}
                            />}
                    </HStack>

                </Box>
            
        </div >
    )
}

