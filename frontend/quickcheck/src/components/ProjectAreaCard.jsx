import React from 'react';
import {
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalBody,
  FormControl,
  FormLabel,
  Input,
  ModalFooter,
  ModalCloseButton,
  ModalHeader,
  Button,
  Link,
  Table,
  Thead,
  Th,
  Tr,
  Td,
  Tfoot,
  Tbody,
  Box,
  Center,
  Text,
  Stack,
  CircularProgress,
  CircularProgressLabel,
} from '@chakra-ui/react';
import Card from './Card.jsx';
import { AddIcon } from '@chakra-ui/icons';
import ShowEditable from '../components/editable.jsx';
export function AddArea(prop) {
  const { isOpen, onOpen, onClose } = useDisclosure();

  const initialRef = React.useRef();
  const finalRef = React.useRef();
  if (prop.editable){
  return (

    <>
      <Button
        onClick={onOpen}
        size="md"
        color="green.900"
        boxShadow={'2xl'}
        rounded={'md'}
        w="50px"
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
      return(<></>);
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
      size="md"
      color="green.900"
      boxShadow={'2xl'}
      rounded={'md'}
      w="50px"
      bg="purple.400"
      p={3}
    >
      ></Button>

      <Modal
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Create Product Area</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <FormControl>
              <FormLabel>Name</FormLabel>
              <Input ref={initialRef} placeholder="Product Area name" />
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

function ProjectArea(prop) {
  return (
    <Tr>
      <Td>
        <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px" bg="green.500" p={3}>
          <Text color={'gray.100'} fontWeight={800} fontSize={'sm'} letterSpacing={1.1}>
          <ShowEditable text={prop.name} editable={prop.editable}></ShowEditable>
          </Text>
        </Box>
      </Td>

      <Td>
        <Box color="white" boxShadow={'2xl'} rounded={'md'} w="20px" p={3}>
          <Text
            color={'gray.100'}
            textTransform={'uppercase'}
            fontWeight={700}
            fontSize={'sm'}
            letterSpacing={1.1}
          >
            <CircularProgress value={prop.percent} color="pink.400">
              <CircularProgressLabel>{prop.percent}%</CircularProgressLabel>
            </CircularProgress>
          </Text>
        </Box>
      </Td>
      <Td>
        <Link to="../projects">
          <Button
            size="lg"
            color="green.900"
            boxShadow={'2xl'}
            rounded={'md'}
            w="100px"
            bg="green.400"
            p={3}
          >
            Open
          </Button>
        </Link>
      </Td>
    </Tr>
  );
}

export default function ProjectAreaCard(prop) {
  return (
    <Card barColor="blue.500">
      <Stack>
        <Text
          color={'green.500'}
          textTransform={'uppercase'}
          fontWeight={800}
          fontSize={'sm'}
          letterSpacing={1.1}
        >
          Product Areas
        </Text>
        <Table variant="simple" size="sm">
          <Thead>
            <Tr>
              <Th>Name</Th>
              <Th>Progress</Th>
              <Th>Files</Th>
            </Tr>
          </Thead>
          <Tbody>
            {prop.areas.map((area) => (
              <ProjectArea name={area.type} role={area.role} percent={area.percent} editable={prop.editable}></ProjectArea>
            ))}
          </Tbody>
          <Tfoot></Tfoot>
        </Table>
        <Center>
          <Link to="../projects"></Link>
          <AddArea editable={prop.editable} question="Create Product Area" default="Product Area Name" ></AddArea>
        </Center>
      </Stack>
    </Card>
  );
}
