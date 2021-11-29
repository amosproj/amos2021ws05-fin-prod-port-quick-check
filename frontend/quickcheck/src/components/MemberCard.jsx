import React from 'react';
import {
  Button,
  Table,
  Thead,
  Th,
  Tr,
  Center,
  Td,
  Tfoot,
  Tbody,
  Box,
  Text,
  Stack,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalFooter,
  ModalCloseButton,
  FormControl,
  FormLabel,
  Input,
  ModalBody,
  ModalHeader,
} from '@chakra-ui/react';
import Card from './Card.jsx';
import ShowEditable from '../components/editable.jsx';
import { DeleteIcon, AddIcon } from '@chakra-ui/icons';

function AddMember(prop) {
  const { isOpen, onOpen, onClose } = useDisclosure();

  const initialRef = React.useRef();
  const finalRef = React.useRef();
  if (prop.editable){
  return (

    <>
      <Button
        onClick={onOpen}
        size="lg"
        color="green.900"
        boxShadow={'2xl'}
        rounded={'md'}
        w="100px"
        bg="purple.400"
        p={3}
      >
      <AddIcon/>
      </Button>

      <Modal
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{prop.question}</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <FormControl>
              <FormLabel>Name</FormLabel>
              <Input ref={initialRef} placeholder={prop.default}/>
            </FormControl>
          </ModalBody>
          <ModalBody pb={6}>
            <FormControl>
              <FormLabel>Role</FormLabel>
              <Input ref={initialRef} placeholder="Member Role"/>
            </FormControl>
          </ModalBody>

          <ModalFooter>
            <Button colorScheme="blue" mr={3}>
              Save
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
  }
  else{
      return (<></>)
  }
  }

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
        <DeleteIcon/>
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
      <EditRemoveButton editable={prop.editable}  name={prop.name} role={prop.role}></EditRemoveButton>
    </Tr>
  );
}
function EditRemoveButton(prop) {
  if (prop.editable) {
    return (
        <Td>
          <Remove name={prop.name} role={prop.role}></Remove>
        </Td>
    );
  } else {
    return (
    <div></div>
);
  }
}

function EditTableHeader(prop) {
  if (prop.editable) {

    return (
        <Thead>
          <Tr>
            <Th>Name</Th>
            <Th>Role</Th>
            <Th> Remove  </Th>

          </Tr>
        </Thead>
    );
  } else {
    return (

    <Thead>
      <Tr>
        <Th>Name</Th>
        <Th>Role</Th>
      </Tr>
    </Thead>);
  }
}

export default function MemberCard(props) {
  return (
    <Card barColor="teal.500">
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
        <EditTableHeader editable={props.editable}></EditTableHeader>
          <Tbody>
            {props.members.map((member) => (
              <MemberRow
                name={member.Name}
                role={member.role}
                editable={props.editable}
              ></MemberRow>
            ))}
          </Tbody>
          <Tfoot></Tfoot>
        </Table>
            <Center>
           <AddMember editable={props.editable} question="Add Member" default="Member Name" ></AddMember>
             </Center>
      </Stack>
    </Card>
  );
}
