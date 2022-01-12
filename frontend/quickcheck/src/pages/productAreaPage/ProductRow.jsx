import {
  Input,
  IconButton,
  Button,
  CircularProgress,
  Spacer,
  Textarea,
  VStack,
  Box,
  Flex,
  Heading,
  List,
} from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions, useStoreState } from 'easy-peasy';

function RemoveButton({ removeProdFct }) {
  return (
    <div>
      <IconButton
        icon={<DeleteIcon />}
        onClick={() => {
          removeProdFct();
        }}
        colorScheme="teal"
        variant="outline"
        size="md"
        color="white"
        bg="red.700"
        w={10}
      />
    </div>
  );
}

function ProductVariant({ productVariant, editMode }) {
  return (
    <Card bg="gray.600" w="full" m={2} justifyContent="space-between">
      <Input
        align="center"
        size="lg"
        w="25%"
        isDisabled={!editMode}
        onChange={(e) => {
          console.log(e.target.value);
        }}
        value={productVariant.productName}
      />
      <Spacer />
      <VStack>
        {/* <CircularProgress size="40px" value={40} /> */}
        <Button variant="whisper">Economical</Button>
      </VStack>
      <Spacer />

      <VStack>
        {/* <CircularProgress size="40px" value={40} /> */}
        <Button variant="whisper">Complexity</Button>
      </VStack>
      <Spacer />
      <Textarea width="30%" placeholder="Anmerkung" />
    </Card>
  );
}

export default function ProductRow({ product, editMode }) {
  const removeProductState = useStoreActions((actions) => actions.productList.removeProduct);
  const getVariants = useStoreState((state) => state.productList.getVariants);

  const productVariants = getVariants(product);

  const removeProduct = () => {
    removeProductState(product);
  };

  return (
    <Card
      direction="column"
      layerStyle="card_bordered"
      justifyContent="space-between"
      // w={(parentID > 0) ? ' 90%' : 'full'}
      _hover={{ boxShadow: '2xl' }}
    >
      <Flex w="full">
        <Input
          align="center"
          size="md"
          w="25%"
          isDisabled={!editMode}
          size="xl"
          onChange={(e) => {
            console.log(e.target.value);
          }}
          value={product.productName}
        />
        <Spacer />
        <VStack>
          <CircularProgress size="40px" value={40} />
          <Button variant="whisper">Economical</Button>
        </VStack>
        <Spacer />

        <VStack>
          <CircularProgress size="40px" value={40} />
          <Button variant="whisper">Complexity</Button>
        </VStack>
        <Spacer />

        <Textarea width="30%" placeholder="Anmerkung" />
        {editMode ? (
          <Box ml={3}>
            <RemoveButton removeProdFct={removeProduct} />
          </Box>
        ) : undefined}
      </Flex>

      <Heading size="lg" mt={8} align="left" w="full" color="yellow">
        Variants
      </Heading>
      {/* <Flex w='full' mt={5}> */}
      <List w="full">
        {productVariants.map((variant) => (
          // <p>{JSON.stringify(variant)}</p>
          <ProductVariant productVariant={variant} editMode={editMode} />
        ))}
      </List>
      {/* </Flex> */}
    </Card>
  );
}
