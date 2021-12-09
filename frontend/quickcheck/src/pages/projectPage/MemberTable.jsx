import React from 'react';
import { Text, useColorModeValue, IconButton, Heading, List, Flex } from '@chakra-ui/react';
import { DeleteIcon } from '@chakra-ui/icons';

import { useStoreActions, useStoreState } from 'easy-peasy';

import { roles } from '../../utils/const';
import Selection from '../../components/Selection.jsx';
import ConfirmClick from '../../components/ConfirmClick';
import AddMemberButton from './AddMemberButton';

function MemberRow({ editMode, member, onChangeRole, ...props }) {
  const bg = useColorModeValue('gray.200', 'gray.600');

  return (
    <Flex {...props} w="full">
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
            onChange={onChangeRole}
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
export default function MemberTable({ editMode, handleChange }) {
  const members = useStoreState((state) => state.project.members);
  const updateProject = useStoreActions((actions) => actions.updateProject);

  const handleUpdateMembers = (members) => updateProject({ members: members });

  const handleRemoveMember = (member) => () => {
    const newMembers = members.filter((m) => m.email !== member.email);
    handleUpdateMembers(newMembers);
  };

  const handleAddMember = (newMember) => {
    handleUpdateMembers([...members, newMember]);
  };

  const handleRoleChange = (member) => (newRole) => {
    // This is a curried function in JS
    let index = members.map((m) => m.email).indexOf(member.email);
    members[index] = { ...member, role: newRole };
    handleUpdateMembers(members);
  };

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
          <AddMemberButton minW={16} size="lg" variant="primary" onAdd={handleAddMember} />
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
            onChangeRole={handleRoleChange(member)}
          ></MemberRow>
          {editMode ? (
            <RemoveButton variant="whisper" minW={16} handleRemove={handleRemoveMember(member)} />
          ) : undefined}
        </Flex>
      ))}
    </List>
  );
}
