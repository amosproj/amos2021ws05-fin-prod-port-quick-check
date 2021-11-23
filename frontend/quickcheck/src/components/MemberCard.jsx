import React from 'react';
import {
  Button,
  Table,
  Thead,
  Th,
  Tr,
  Td,
  Tfoot,
  Tbody,
  Box,
  Text,
  Stack,
  Editable,
  EditableInput,
  EditablePreview,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalFooter,
  ModalCloseButton,
  ModalHeader,
} from '@chakra-ui/react';
import BaseCard from './BaseCard.jsx';
import ShowEditable from '../components/editable.jsx';
function Remove(prop) {
  const { isOpen, onOpen, onClose } = useDisclosure();

  const initialRef = React.useRef();
  const finalRef = React.useRef();

  return (
    <>
      <Button
        onClick={onOpen}
        size="lg"
        color="red.900"
        boxShadow={'2xl'}
        rounded={'md'}
        w="100px"
        bg="red.400"
        p={3}
      >
        REMOVE
      </Button>

      <Modal
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>
            Are you sure you want to remove the {prop.role}, {prop.name}, from the project?
          </ModalHeader>
          <ModalCloseButton />
          <ModalFooter>
            <Button colorScheme="red" mr={3}>
              Yes
            </Button>
            <Button colorScheme="green" onClick={onClose}>
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}

function MemberRow(prop) {
  return (
    <Tr>
      <Td>
        <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px" bg="blue.500" p={3}>
          <Text color={'gray.100'} fontWeight={800} fontSize={'sm'} letterSpacing={1.1}>
             <ShowEditable text={prop.name} editable={prop.editable}></ShowEditable>
          </Text>
        </Box>
      </Td>

      <Td>
        <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px" bg="green.500" p={3}>
          <Text
            color={'gray.100'}
            textTransform={'uppercase'}
            fontWeight={700}
            fontSize={'sm'}
            letterSpacing={1.1}
          >
          <ShowEditable text={prop.role} editable={prop.editable}></ShowEditable>

          </Text>
        </Box>
      </Td>
      <Td>
        <Remove name={prop.name} role={prop.role}></Remove>
      </Td>
    </Tr>
  );
}

export default function MemberCard(props) {
  return (
    <BaseCard barColor="teal.500">
      <Stack>
        <Text
          color={'green.500'}
          textTransform={'uppercase'}
          fontWeight={800}
          fontSize={'sm'}
          letterSpacing={1.1}
        >
          Members
        </Text>
        <Table variant="simple" size="sm">
          <Thead>
            <Tr>
              <Th>Name</Th>
              <Th>Role</Th>
              <Th> Remove</Th>
            </Tr>
          </Thead>
          <Tbody>

            {props.Members.map((member) => (
              <MemberRow name={member.Name} role={member.role} editable={props.editable}></MemberRow>
            ))}
          </Tbody>
          <Tfoot></Tfoot>
        </Table>
      </Stack>
    </BaseCard>
  );
}
