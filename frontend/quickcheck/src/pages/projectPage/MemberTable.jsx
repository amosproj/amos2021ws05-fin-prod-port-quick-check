import React from 'react';
import { Text, useColorModeValue, IconButton, Heading, List, Flex } from '@chakra-ui/react';
import { DeleteIcon } from '@chakra-ui/icons';

import { useStoreActions, useStoreState } from 'easy-peasy';

import { roles } from '../../utils/const';
import Selection from '../../components/Selection.jsx';
import ConfirmClick from '../../components/ConfirmClick';
import AddMemberButton from './AddMemberButton';

function MemberRow({ editMode, member, ...rest }) {
  const updateMember = useStoreActions((actions) => actions.project.updateMember);
  const handleRoleChange = (newRole) => {
    updateMember({ ...member, role: newRole });
  };

  const bg = useColorModeValue('gray.200', 'gray.600');

  return (
    <Flex {...rest} w="full">
      <Text variant="cell" align="left" w="full">
        {member.email}
      </Text>
      <Flex w={60}>
        {editMode ? (
          <Selection
            bg={bg}
            border="0px"
            selected={member.role}
            options={Object.values(roles)}
            onChange={handleRoleChange}
            w="full"
          />
        ) : (
          <Text variant="cell" align="left" w="full">
            {member.role}
          </Text>
        )}
      </Flex>
    </Flex>
  );
}

const RemoveButton = ({ handleRemove, ...buttonProps }) => {
  return (
    <ConfirmClick onConfirm={handleRemove} confirmPrompt="Remove this product area?">
      <IconButton icon={<DeleteIcon />} {...buttonProps} />
    </ConfirmClick>
  );
};
// Assumption: ProjectMembers is a list of object: {id, role}

export default function MemberTable({ editMode }) {
  const members = useStoreState((state) => state.project.data.members);
  const addMember = useStoreActions((actions) => actions.project.addMember);
  const removeMember = useStoreActions((actions) => actions.project.removeMember);
  const bgHeading = useColorModeValue('gray.400', 'gray.500');

  return (
    <List spacing={2} direction="column" w="full" align="center" maxW={700}>
      <Flex gridGap={3} w="full">
        <Flex gridGap={3} h={12} w="full">
          <Heading size="md" rounded="md" pt={2} bg={bgHeading} w="full">
            Email
          </Heading>
          <Heading size="md" rounded="md" pt={2} bg={bgHeading} w={60}>
            Role
          </Heading>
        </Flex>
        {editMode ? (
          <AddMemberButton minW={16} size="lg" variant="primary" onAdd={addMember} />
        ) : undefined}
      </Flex>

      {members.map((member) => (
        <Flex gridGap={3}>
          <MemberRow
            rounded="md"
            align="center"
            w="full"
            gridGap={3}
            key={member.email}
            member={member}
            editMode={editMode}
          ></MemberRow>
          {editMode ? (
            <RemoveButton variant="whisper" minW={16} handleRemove={() => removeMember(member)} />
          ) : undefined}
        </Flex>
      ))}
    </List>
  );
}
