import {
  Input,
  IconButton,
  Button,
  CircularProgress,
  Spacer,
  Textarea,
  VStack,
  Box
} from '@chakra-ui/react';
import React from 'react';
import Card from './Card';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions } from 'easy-peasy';

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

export default function ProductRow({ product, editMode }) {
  const removeProductState = useStoreActions((actions) => actions.productList.removeProduct);

  const removeProduct = () => {
    removeProductState(product);
  };

  return (
    <div>
      <Card
        layerStyle="card_bordered"
        justifyContent="space-between"
        // w={(parentID > 0) ? ' 90%' : 'full'}
        _hover={{ boxShadow: '2xl' }}
      >

        <Input
          align="center"
          size="md"
          w="25%"
          isDisabled={!editMode}
          onChange={(e) => {
            console.log(e.target.value);
          }}
          value={product.productName}
        />
        <Spacer />
        <VStack>
          <CircularProgress size='40px' value={40} />
          <Button variant="whisper">Economical</Button>
        </VStack>
        <Spacer />

        <VStack>
          <CircularProgress size='40px' value={40} />
          <Button variant="whisper">Complexity</Button>
        </VStack>
        <Spacer />

        <Textarea bg={'gray.600'}  width="30%" placeholder="Anmerkung" />
        {editMode ? <Box ml={3}><RemoveButton removeProdFct={removeProduct} /></Box> : undefined}

      </Card>

    </div>
  );
}
