import {
  Input,
  IconButton,
  Button,
  CircularProgress,
  Spacer,
  Textarea,
  VStack,
  Progress,
  HStack,
  Flex,
  Heading,
  List,
  Link,
  Collapse,
  useDisclosure,
} from '@chakra-ui/react';

import { ArrowForwardIcon } from '@chakra-ui/icons';

import React from 'react';
import Card from '../../components/Card';
import { useStoreActions, useStoreState } from 'easy-peasy';
import ProductVariant from './ProductVariant';

export default function ProductRow({ product, editMode }) {
  const { isOpen, onToggle } = useDisclosure();
  // const removeProductState = useStoreActions((actions) => actions.productList.removeProduct);
  const changeProductName = useStoreActions((actions) => actions.productList.changeProductName);
  const changeProductComment = useStoreActions(
    (actions) => actions.productList.changeProductComment
  );

  const getVariants = useStoreState((state) => state.productList.getVariants);
  const productVariants = getVariants(product);

  const setProduct = (productName) => {
    product.productName = productName;
    changeProductName(product);
  };
  const handleTextInputChange = (comment) => {
    product.comment = comment;
    changeProductComment(product);
  };

  return (
    <Card
      layerStyle="card_bordered"
      justifyContent="space-between"
      direction="column"
      // w={(parentID > 0) ? ' 90%' : 'full'}
      _hover={{ boxShadow: '2xl' }}
    >
      <Flex direction="column" w="full" justifyContent="space-between" onClick={onToggle}>
        <Flex w="full" mb={3}>
          <Input
            variant="bold"
            align="center"
            size="2xl"
            isDisabled={!editMode}
            onChange={(e) => {
              setProduct(e.target.value);
            }}
            value={product.productName}
          />
          <Spacer />

          <HStack>
            <CircularProgress size="40px" value={product.progressEconomic} />

            <Link href="/ratings">
              <Button variant="whisper">Economical</Button>
            </Link>
          </HStack>

          <Spacer />

          <HStack ml="5%">
            <CircularProgress size="40px" value={product.progressComplexity} />
            <Link href="/ratings">
              <Button variant="whisper" href="/ratings">
                Complexity
              </Button>
            </Link>
          </HStack>
        </Flex>

        <Flex w="full" align="center">
          <Button rightIcon={<ArrowForwardIcon />}>Variants ()</Button>
          <Spacer />

          <Textarea
            width="50%"
            isReadOnly={!editMode}
            value={product.comment !== null ? product.comment : ''}
            onChange={(e) => {
              handleTextInputChange(e.target.value);
            }}
            placeholder="Anmerkung"
          />
          <Spacer />
          <Button>Upload</Button>
        </Flex>
      </Flex>

      <Collapse in={isOpen} w="full" animateOpacity>
        <Flex w="full" mt={5}>
          <List w="full">
            {productVariants.map((variant) => (
              // <p>{JSON.stringify(variant)}</p>
              <ProductVariant product={variant} editMode={editMode} key={variant.productID} />
            ))}
          </List>
        </Flex>
      </Collapse>
    </Card>
  );
}
