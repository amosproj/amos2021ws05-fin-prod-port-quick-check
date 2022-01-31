<<<<<<< HEAD
import {
  Input,
  Button,
  Textarea,
  Link,
  CircularProgress,
  Flex,
  useColorModeValue,
  VStack,
  HStack,
} from '@chakra-ui/react';
=======
import { Input, Button, Spacer, Textarea, Link, Flex, useColorModeValue } from '@chakra-ui/react';
>>>>>>> 126cf677def1a9e24212b00498480b7b468e4c34
import { React, useState } from 'react';
import { AttachmentIcon } from '@chakra-ui/icons';
import Card from '../../components/Card';
import { useStoreActions } from 'easy-peasy';

export default function ProductVariant({ product, editMode }) {
  const bg = useColorModeValue('gray.100', 'gray.600');
  const [validName, setValidName] = useState(true);
  const updateProductName = useStoreActions((actions) => actions.productList.updateProductName);
  const updateProductComment = useStoreActions(
    (actions) => actions.productList.updateProductComment
  );

  const setName = (newName) => {
    setValidName(newName !== '');
    updateProductName({ productID: product.productID, newName });
  };
  const setComment = (newComment) => {
    updateProductComment({ productID: product.productID, newComment });
  };

  return (
    <Card
      bg={bg}
      w="full"
      m={2}
      justifyContent="space-between"
      direction="column"
      _hover={{ boxShadow: 'lg' }}
    >
      <Flex direction="row" w="full" justifyContent="space-between" alignItems={"center"}>

        <Input
          align="center"
          size="lg"
          w="35%"
          variant="bold"
          borderColor={'gray.500'}
          borderWidth={editMode ? 1 : 0}
          mr={1}
          isInvalid={!validName}
          isDisabled={!editMode}
          onChange={(e) => {
            setName(e.target.value);
          }}
          value={product.productName}
        />
        <VStack>
          <HStack>
          <CircularProgress size="35px" value={product.progressEconomic} />
            <Link
              href={`/projects/${product.projectID}/productArea/${product.productArea.id}/products/${product.productID}/ratings/economic`}
            >
              <Button size="sm" variant="whisper">Economical</Button>
            </Link>
          </HStack>
          <HStack>
          <CircularProgress size="35px" value={product.progressComplexity} />
          <Link
            href={`/projects/${product.projectID}/productArea/${product.productArea.id}/products/${product.productID}/ratings/complexity`}
          >
            <Button size="sm" variant="whisper">Complexity</Button>
          </Link>
          </HStack>
        </VStack>
        <Textarea
          w="40%"
          isReadOnly={!editMode}
          value={product.comment !== null ? product.comment : ''}
          onChange={(e) => {
            setComment(e.target.value);
          }}
          placeholder="Anmerkung"
        />
      </Flex>
<<<<<<< HEAD
      <Flex w="full" justifyContent={"flex-end"} mt="1">
        <Button size="xs" leftIcon={<AttachmentIcon />} variant="whisper">Reference</Button>
=======
      <Flex direction="row" w="full">
        <Flex w="50%">
          <Spacer />

          <Link
            href={`/projects/${product.projectID}/productArea/${product.productArea.id}/products/${product.productID}/ratings/economic`}
          >
            <Button variant="whisper">Economical</Button>
          </Link>
          <Spacer />

          <Link
            href={`/projects/${product.projectID}/productArea/${product.productArea.id}/products/${product.productID}/ratings/complexity`}
          >
            <Button variant="whisper">Complexity</Button>
          </Link>
          <Spacer />
        </Flex>
        <Flex w="50%">
          <Spacer />
          <Button leftIcon={<AttachmentIcon />} variant="whisper">
            Reference
          </Button>
        </Flex>
>>>>>>> 126cf677def1a9e24212b00498480b7b468e4c34
      </Flex>
    </Card>
  );
}
