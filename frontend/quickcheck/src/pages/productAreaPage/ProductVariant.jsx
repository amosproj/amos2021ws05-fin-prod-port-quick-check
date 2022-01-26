import { Input, Button, Spacer, Textarea, VStack } from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import { useStoreActions } from 'easy-peasy';

export default function ProductVariant({ product, editMode }) {
  const changeProductName = useStoreActions((actions) => actions.productList.changeProductName);
  const changeProductComment = useStoreActions(
    (actions) => actions.productList.changeProductComment
  );

  const setProduct = (productName) => {
    product.productName = productName;
    changeProductName(product);
  };
  const handleTextInputChange = (comment) => {
    product.comment = comment;
    changeProductComment(product);
  };

  return (
    <Card bg="gray.600" w="full" m={2} justifyContent="space-between">
      <Input
        align="center"
        size="lg"
        w="25%"
        isDisabled={!editMode}
        onChange={(e) => {
          setProduct(e.target.value);
        }}
        value={product.productName}
      />
      <Spacer />
      <VStack>
        <Button variant="whisper">Economical</Button>
      </VStack>
      <Spacer />

      <VStack>
        <Button variant="whisper">Complexity</Button>
      </VStack>
      <Spacer />
      <Textarea
        w="30%"
        isReadOnly={!editMode}
        value={product.comment !== null ? product.comment : ''}
        onChange={(e) => {
          handleTextInputChange(e.target.value);
        }}
        placeholder="Anmerkung"
      />
    </Card>
  );
}
